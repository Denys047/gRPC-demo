package com.example.service;

import com.example.repository.AccountRepository;
import com.example.service.handler.TransferHandler;
import com.grpc.service.TransferRequest;
import com.grpc.service.TransferResponse;
import com.grpc.service.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //biDirectional streaming
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
       return new TransferHandler(responseObserver,accountRepository);
    }

}
