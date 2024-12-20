package com.example.service.handler;

import com.example.model.ResultModel;
import com.grpc.service.GuessRequest;
import com.grpc.service.GuessResponse;
import com.grpc.service.Result;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class GuessHandler implements StreamObserver<GuessRequest> {

    private final StreamObserver<GuessResponse> responseObserver;

    private Integer attempt;

    private final int secret;

    public GuessHandler(StreamObserver<GuessResponse> responseObserver) {
        this.responseObserver = responseObserver;
        this.attempt = 0;
        secret = ThreadLocalRandom.current().nextInt(1, 101);
    }

    @Override
    public void onNext(GuessRequest value) {
        var result = getResult(value);
        responseObserver.onNext(GuessResponse.newBuilder().setAttempt(attempt).setResult(Result.valueOf(result.name())).build());

        if (result.equals(ResultModel.CORRECT)) {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        this.responseObserver.onCompleted();
    }

    private ResultModel getResult(GuessRequest value) {
        attempt++;
        int clientNumber = value.getGuess();
        if (clientNumber == secret) {
            return ResultModel.CORRECT;
        }
        return clientNumber > secret ? ResultModel.TOO_HIGH : ResultModel.TOO_LOW;
    }

}
