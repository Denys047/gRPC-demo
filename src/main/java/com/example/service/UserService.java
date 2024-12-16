package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.google.protobuf.Empty;
import com.grpc.service.*;
import io.grpc.stub.StreamObserver;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserById(FindUserByIdRequest request, StreamObserver<UserResponse> responseObserver) {
        userRepository.findById(request.getId())
                .ifPresentOrElse(user -> {
                    responseObserver.onNext(userModelToGrpcModel(user));
                    responseObserver.onCompleted();
                }, () -> responseObserver.onError(new RuntimeException("User not found")));
    }

    @Override
    public void getAllUsers(Empty request, StreamObserver<AllUsersResponse> responseObserver) {
        responseObserver.onNext(AllUsersResponse
                .newBuilder()
                .addAllUsers(userRepository.findAll().stream().map(this::userModelToGrpcModel).toList())
                .build());
        responseObserver.onCompleted();
    }

    private UserResponse userModelToGrpcModel(User user) {
        return UserResponse.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setRole(Role.valueOf(user.getRole().name()))
                .build();
    }

}
