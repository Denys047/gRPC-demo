package com.example;

import com.grpc.proto.service.user.UserRequest;
import com.grpc.proto.service.user.UserResponse;
import com.grpc.proto.service.user.UserServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.Duration;

public class GrpcClient {

    private static final Channel channel = ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build();
    private static final UserRequest userRequest = UserRequest.newBuilder().setId(1L).build();

    public static void main(String[] args) throws InterruptedException {
//        requestAsyncStub();
//        requestBlockStub();
    }

    public static void requestBlockStub() {
        var blockStub = UserServiceGrpc.newBlockingStub(channel);
        var userResponse = blockStub.getUser(userRequest);
        System.out.println("block response: " + userResponse);
    }

    public static void requestAsyncStub() throws InterruptedException {
        var asyncStub = UserServiceGrpc.newStub(channel);

        asyncStub.getUser(userRequest, new StreamObserver<UserResponse>() {

            @Override
            public void onNext(UserResponse value) {
                System.out.println("async response: " + value.toString());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("async stub done");
            }
        });
        Thread.sleep(Duration.ofSeconds(5));
    }

}
