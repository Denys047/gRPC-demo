package com.example.client;

import com.grpc.service.FindUserByIdRequest;
import com.grpc.service.UserServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class ClientGrpc {

    public static void main(String[] args) {
        Channel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        var unaryStub = UserServiceGrpc.newBlockingStub(channel);
        var request = FindUserByIdRequest.newBuilder().setId(1).build();
        var response = unaryStub.getUserById(request);
        System.out.println(response);
    }

}
