package ru.otus.services.processors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    private final BlockingQueue<SensorData> bufferedData;
    private final ReentrantLock flushLock = new ReentrantLock();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;

        bufferedData = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));

    }

    @Override
    public void process(SensorData data) {

        bufferedData.add(data);
        if (bufferedData.size() >= bufferSize) {
            flush();
        }

    }

    public void flush() {

        flushLock.lock();
        try {

            if (bufferedData.isEmpty()) {
                return;
            }

            List<SensorData> dataToFlush = new ArrayList<>();
            bufferedData.drainTo(dataToFlush);

            writer.writeBufferedData(dataToFlush);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        } finally {
            flushLock.unlock();
        }

    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

}
