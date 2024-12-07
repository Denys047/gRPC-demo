package com.example;

import com.grpc.proto.service.user.UserRequest;
import com.grpc.proto.service.user.UserServiceGrpc;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        var response = stub.getUser(UserRequest.newBuilder().setId(2L).build());

        System.out.println(response.toString());
    }

}
