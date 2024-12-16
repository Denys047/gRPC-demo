package com.example.service.handler;

import com.example.utility.SumNumber;
import com.grpc.service.SumNumbersRequest;
import com.grpc.service.SumNumbersResponse;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class SumNumberHandler implements StreamObserver<SumNumbersRequest> {

    private final StreamObserver<SumNumbersResponse> responseObserver;

    private final SumNumber sumNumber;

    private final Logger logger = Logger.getLogger(SumNumberHandler.class.getName());

    public SumNumberHandler(StreamObserver<SumNumbersResponse> responseObserver, SumNumber sumNumber) {
        this.responseObserver = responseObserver;
        this.sumNumber = sumNumber;
    }

    @Override
    public void onNext(SumNumbersRequest value) {
        sumNumber.addNumber(value.getNumber());
        logger.info("number added: " + value.getNumber());
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        var sumNumberResponse = SumNumbersResponse.newBuilder().setResult(sumNumber.getSum()).build();
        this.responseObserver.onNext(sumNumberResponse);
        this.responseObserver.onCompleted();
    }

}
