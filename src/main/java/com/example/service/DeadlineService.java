package com.example.service;

import com.example.validator.RequestValidator;
import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.service.DeadlineRequest;
import com.grpc.service.DeadlineResponse;
import com.grpc.service.DeadlineServiceGrpc;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DeadlineService extends DeadlineServiceGrpc.DeadlineServiceImplBase {

    private static final Logger logger = Logger.getLogger(DeadlineService.class.getName());

    @Override
    public void getMessage(DeadlineRequest request, StreamObserver<DeadlineResponse> responseObserver) {
        RequestValidator.validateIdMetadata(request.getId())
                .ifPresentOrElse(responseObserver::onError, () -> {
                    Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
                    responseObserver.onNext(toProtoModel(request.getId(), "Message %s".formatted(request.getId())));
                    responseObserver.onCompleted();
                });
    }

    @Override
    public void getStreamOfMessage(DeadlineRequest request, StreamObserver<DeadlineResponse> responseObserver) {
        RequestValidator.validateIdMetadata(request.getId())
                .ifPresentOrElse(responseObserver::onError, () -> {
                    for (int i = 0; i < 10 && !Context.current().isCancelled(); i++) {
                        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
                        responseObserver.onNext(toProtoModel(request.getId(), "Message %s".formatted(request.getId() + i)));
                    }
                    logger.info("stream ended");
                    responseObserver.onCompleted();
                });
    }


    private DeadlineResponse toProtoModel(long id, String message) {
        return DeadlineResponse.newBuilder().setId(id).setMessage(message).build();
    }

}
