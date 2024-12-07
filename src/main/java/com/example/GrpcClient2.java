package com.example;

import com.google.protobuf.Empty;
import com.grpc.proto.service.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient2 {

    private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();

    public static void main(String[] args) {
        unaryRequest();
    }

    public static void unaryRequest() {
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
        var result = stub.getAllUser(Empty.newBuilder().build());
        System.out.println(result);
    }

}
