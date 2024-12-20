package com.example.service;

import com.example.service.handler.FlowControlHandler;
import com.grpc.service.FlowControlServiceGrpc;
import com.grpc.service.Output;
import com.grpc.service.RequestSize;
import io.grpc.stub.StreamObserver;

public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {

    @Override
    public StreamObserver<RequestSize> getMessage(StreamObserver<Output> responseObserver) {
        return new FlowControlHandler(responseObserver);
    }

}
