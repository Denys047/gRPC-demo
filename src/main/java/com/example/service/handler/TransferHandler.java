package com.example.service.handler;

import com.example.converter.ProtoConverter;
import com.example.model.TransferStatusModel;
import com.example.repository.AccountRepository;
import com.grpc.service.TransferRequest;
import com.grpc.service.TransferResponse;
import com.grpc.service.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferHandler implements StreamObserver<TransferRequest> {

    private final StreamObserver<TransferResponse> responseObserver;

    private final AccountRepository accountRepository;

    public TransferHandler(StreamObserver<TransferResponse> responseObserver, AccountRepository accountRepository) {
        this.responseObserver = responseObserver;
        this.accountRepository = accountRepository;
    }

    @Override
    public void onNext(TransferRequest value) {
        var transferStatus = status(value);
        var accountFromDb = accountRepository.getAccount(value.getFromAccount());
        var accountToDb = accountRepository.getAccount(value.getToAccount());

        responseObserver
                .onNext(TransferResponse.newBuilder()
                        .setFromAccount(ProtoConverter.accountModelToProtoModel(accountFromDb))
                        .setToAccount(ProtoConverter.accountModelToProtoModel(accountToDb))
                        .setStatus(TransferStatus.valueOf(transferStatus.name())).build());
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {
        this.responseObserver.onCompleted();
    }

    private TransferStatusModel status(TransferRequest value) {
        var accountFromId = value.getFromAccount();
        var accountToId = value.getToAccount();

        var accountFromDb = accountRepository.getAccount(accountFromId);
        var accountToDb = accountRepository.getAccount(accountToId);

        if ((accountFromDb.getAmount() >= value.getAmount()) && (accountFromId != accountToId)) {
            accountRepository.transferAccount(accountFromDb, accountToDb, value.getAmount());
            return TransferStatusModel.COMPLETED;
        }

        return TransferStatusModel.REJECTED;
    }

}
