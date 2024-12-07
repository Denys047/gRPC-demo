package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.google.protobuf.Empty;
import com.grpc.proto.service.user.AllUsersResponse;
import com.grpc.proto.service.user.UserRequest;
import com.grpc.proto.service.user.UserResponse;
import com.grpc.proto.service.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.stream.Collectors;

public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            var userModel = userRepository.getUser(request.getId());
            responseObserver.onNext(toGrpcModel(userModel));
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void getAllUser(Empty request, StreamObserver<AllUsersResponse> responseObserver) {
        try {
            var usersModels = userRepository.getAllUsers();
            responseObserver.onNext(AllUsersResponse
                    .newBuilder()
                    .addAllUserResponse(usersModels.stream().map(this::toGrpcModel).collect(Collectors.toList()))
                    .build());
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    private UserResponse toGrpcModel(User userModel) {
        return UserResponse.newBuilder()
                .setId(userModel.id())
                .setEmail(userModel.email())
                .setName(userModel.name())
                .build();
    }

}
