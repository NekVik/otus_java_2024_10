package ru.otus;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReentrantLockMain {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockMain.class);

    private Integer number = 1;
    private boolean ascending = true;
    private boolean firstThreadPrint = true;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();


    private void printNumbers(boolean firstThread) {
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                // Ждем своей очереди
                while ((firstThread && !firstThreadPrint) || (!firstThread && firstThreadPrint)) {
                    condition.await();
                }

                logger.info(number.toString());

                // После печати второго потока обновляем число
                if (!firstThread) {
                    if ((number >= 1 && number < 10) && ascending) {
                        number++;
                        if (number == 10) {
                            ascending = false;
                        }
                    } else {
                        number--;
                        if (number == 1) {
                            ascending = true;
                        }
                    }
                }

                firstThreadPrint = !firstThreadPrint;

                sleep();
                condition.signalAll();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ReentrantLockMain threadMain = new ReentrantLockMain();
        new Thread(() -> threadMain.printNumbers(true)).start();
        new Thread(() -> threadMain.printNumbers(false)).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(5_00);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
