package com.example.example1;

import com.example.grpc.proto.user.v1.UserOuterClass;

import java.util.logging.Logger;

public class Example1 {

    private static final Logger logger = Logger.getLogger(Example1.class.getName());

    public static void main(String[] args) {
        var user = UserOuterClass.User.newBuilder()
                .setAge(10)
                .setName("John")
                .build();

        logger.info(user.toString());
    }

}
