package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadMain {

    private static final Logger logger = LoggerFactory.getLogger(ThreadMain.class);

    private Integer number = 1;
    private boolean ascending = true;
    private boolean firstThreadPrint = true;


    private synchronized void printNumbers(boolean firstThread){

        while (!Thread.currentThread().isInterrupted()) {

            try {

                while ((firstThread && !firstThreadPrint) || (!firstThread && firstThreadPrint) ) {
                    this.wait();
                }

                logger.info(number.toString());

                if (!firstThreadPrint) {

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

                firstThreadPrint = !firstThread;

                sleep();
                this.notifyAll();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }


    public static void main(String[] args) {
        ThreadMain threadMain = new ThreadMain();
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
