package ru.otus;

import io.grpc.ManagedChannelBuilder;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.ClientRequest;
import ru.otus.protobuf.RemoteDBServiceGrpc;
import ru.otus.service.ClientStreamObserver;

@SuppressWarnings({"squid:S106", "squid:S2142"})
public class GRPCClient {

    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);

    private static final int LAST_VALUE = 30;
    private static final int COUNT_ITERATIONS = 50;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        log.info("Sending request...");
        var request = ClientRequest.newBuilder()
                .setFirstValue(0)
                .setLastValue(LAST_VALUE)
                .build();

        int currentValue = 0;
        var queue = new LinkedList<Integer>();

        var stub = RemoteDBServiceGrpc.newStub(channel);
        var latch = new CountDownLatch(1);

        stub.serverStreamingValue(request, new ClientStreamObserver(queue, latch));

        for (var i = 0; i < COUNT_ITERATIONS; i++) {

            int lastServerValue = Optional.ofNullable(queue.poll()).orElse(0);
            currentValue = currentValue + lastServerValue + 1;
            log.info("currentValue: {}", currentValue);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error", e);
            }
        }
        latch.await();
        log.info("Received all values.");

        channel.shutdown();
        log.info("Closed channel.");
    }

}
