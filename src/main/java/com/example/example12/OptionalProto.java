package com.example.example12;

import org.example.grpc.proto.optionalproto.UserRequest;

public class OptionalProto {

    public static void main(String[] args) {
        UserRequest userRequest = UserRequest.newBuilder().setName("John").setAge(12).setMessage("asd").build();
        System.out.println(userRequest.hasAge());
        System.out.println(userRequest.hasName());
        System.out.println(userRequest.getMessage().isEmpty() ? "user message empty" : userRequest.getMessage());

        System.out.println("-----------------");
        UserRequest userRequest1 = UserRequest.newBuilder().build();
        System.out.println(userRequest1.hasAge());
        System.out.println(userRequest1.hasName());
        System.out.println(userRequest1.getMessage().isEmpty() ? "user message empty" : userRequest1.getMessage());
    }

}
