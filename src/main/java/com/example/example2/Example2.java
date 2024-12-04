package com.example.example2;

import com.example.grpc.proto.user.v3.User;

import java.util.logging.Logger;

public class Example2 {

    private static final Logger logger = Logger.getLogger(Example2.class.getName());

    public static void main(String[] args) {
        User user = User.newBuilder().setFirstName("Alex").build();

        logger.info(user.toString());
    }

}
