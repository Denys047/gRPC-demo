package com.example.service;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.service.CallOptionsServiceGrpc;
import com.grpc.service.Request;
import com.grpc.service.Response;
import io.grpc.stub.StreamObserver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallOptionsService extends CallOptionsServiceGrpc.CallOptionsServiceImplBase {

    @Override
    public void callOptions(Request request, StreamObserver<Response> responseObserver) {
        var number = getRandomNumber();
        Uninterruptibles.sleepUninterruptibly(3L, TimeUnit.SECONDS);
        responseObserver.onNext(Response.newBuilder().setMessage(request.getMessage() + " " + number).setNumber(number).build());
        responseObserver.onCompleted();
    }

    private Long getRandomNumber() {
        return new Random().nextLong();
    }

}
