package ru.otus.service;

import io.grpc.stub.StreamObserver;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.ValueResponse;

public class ClientStreamObserver implements StreamObserver<ValueResponse> {

    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);

    private final Queue<Integer> queue;
    private final CountDownLatch latch;

    public ClientStreamObserver(Queue<Integer> queue, CountDownLatch latch) {
        this.queue = queue;
        this.latch = latch;
    }

    @Override
    public void onNext(ValueResponse value) {
        queue.offer(value.getNewValue());
        log.info("Received value: {}", value.getNewValue());
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error", t);
    }

    @Override
    public void onCompleted() {
        latch.countDown();
    }
}
