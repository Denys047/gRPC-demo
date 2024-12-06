package com.example;

import com.example.server.GrpcServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer.startServer();
    }

}