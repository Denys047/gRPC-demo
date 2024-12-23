package com.example.service;

import com.google.protobuf.Empty;
import com.grpc.service.KeyApiResponse;
import com.grpc.service.SecurityServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SecurityService extends SecurityServiceGrpc.SecurityServiceImplBase {

    @Override
    public void getStatusApi(Empty empty, StreamObserver<KeyApiResponse> responseObserver) {
        responseObserver.onNext(KeyApiResponse.newBuilder().setStatus(true).build());
        responseObserver.onCompleted();
    }

}
