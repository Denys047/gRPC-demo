package com.example.service;

import com.example.service.handler.GuessHandler;
import com.grpc.service.GuessNumberGrpc;
import com.grpc.service.GuessRequest;
import com.grpc.service.GuessResponse;
import io.grpc.stub.StreamObserver;

public class GuessService extends GuessNumberGrpc.GuessNumberImplBase {

    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
        return new GuessHandler(responseObserver);
    }

}
