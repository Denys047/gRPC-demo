package com.example.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.List;

public class GrpcServer {

    private final Server server;

    private GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer createServer(int port, List<BindableService> serviceList) {
        var grpcServer = ServerBuilder.forPort(port);
        serviceList.forEach(grpcServer::addService);
        return new GrpcServer(grpcServer.build());
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        server.awaitTermination();
    }

}
