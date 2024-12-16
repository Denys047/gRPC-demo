package com.example.service;

import com.example.converter.ProtoConverter;
import com.example.repository.UserRepository;
import com.example.service.handler.SumNumberHandler;
import com.example.utility.RandomNumberGenerator;
import com.example.utility.SumNumber;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import com.grpc.service.*;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserById(FindUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
        userRepository.findById(request.getId())
                .ifPresentOrElse(user -> {
                    responseObserver.onNext(ProtoConverter.userModelToProtoModel(user));
                    responseObserver.onCompleted();
                }, () -> responseObserver.onError(new RuntimeException("User not found")));
    }

    @Override
    public void getAllUsers(Empty request, StreamObserver<AllUsersResponse> responseObserver) {
        responseObserver.onNext(AllUsersResponse
                .newBuilder()
                .addAllUsers(userRepository.findAll().stream().map(ProtoConverter::userModelToProtoModel).toList())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(UserRequest request, StreamObserver<RandomNumber> responseObserver) {
        long origin = request.getOrigin();
        long bound = request.getBound();

        for (int i = 0; i < 10; i++) {
            var randomNumber = RandomNumberGenerator.generateLong(origin, bound);
            responseObserver.onNext(RandomNumber.newBuilder().setNumber(randomNumber).build());
//            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<SumNumbersRequest> sum(StreamObserver<SumNumbersResponse> responseObserver) {
        return new SumNumberHandler(responseObserver,new SumNumber());
    }

}
