package com.example.server;

import com.example.repository.UserRepository;
import com.example.service.UserService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void startServer() throws IOException, InterruptedException {
        var server = ServerBuilder.forPort(8082)
                .addService(new UserService(new UserRepository()))
                .build();

        server.start();
        server.awaitTermination();
    }

}
