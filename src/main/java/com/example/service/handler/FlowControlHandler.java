package com.example.service.handler;

import com.grpc.service.Output;
import com.grpc.service.RequestSize;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;
import java.util.stream.IntStream;

public class FlowControlHandler implements StreamObserver<RequestSize> {

    private final StreamObserver<Output> responseObserver;

    private static final Logger logger = Logger.getLogger(FlowControlHandler.class.getName());

    private Integer emitted;

    public FlowControlHandler(StreamObserver<Output> responseObserver) {
        this.responseObserver = responseObserver;
        this.emitted = 0;
    }

    @Override
    public void onNext(RequestSize value) {
        IntStream.rangeClosed((emitted + 1), 100)
                .limit(value.getSize())
                .forEach(i -> {
                    logger.info("emitting: %s".formatted(i));
                    responseObserver.onNext(Output.newBuilder().setValue(i).build());
                });
        emitted = emitted + value.getSize();
        if (emitted >= 100) {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }

}
