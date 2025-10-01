package ru.otus.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.ClientRequest;
import ru.otus.protobuf.RemoteDBServiceGrpc;
import ru.otus.protobuf.ValueResponse;

@SuppressWarnings("java:S2142")
public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(RemoteDBServiceImpl.class);

    @Override
    public void serverStreamingValue(ClientRequest request, StreamObserver<ValueResponse> responseObserver) {
        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        for (int i = firstValue + 1; i <= lastValue; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("Error", e);
            }
            var value = ValueResponse.newBuilder().setNewValue(i).build();

            responseObserver.onNext(value);
        }
        responseObserver.onCompleted();
    }
}
