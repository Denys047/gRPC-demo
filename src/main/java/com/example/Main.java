package com.example;

import com.example.repository.UserRepository;
import com.example.server.GrpcServer;
import com.example.service.UserService;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        var grpcServer = GrpcServer.createServer(8082, List.of(new UserService(new UserRepository())));
        grpcServer.start();
    }

}