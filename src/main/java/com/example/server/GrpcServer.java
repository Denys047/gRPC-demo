package com.example.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GrpcServer {

    private final Server server;

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    private GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer create(List<BindableService> serviceDefinitions) {
        return GrpcServer.create(8080, serviceDefinitions);
    }

    public static GrpcServer create(int port, List<BindableService> serviceDefinitions) {
        var builder = ServerBuilder.forPort(port)
                .addServices(serviceDefinitions.stream().map(BindableService::bindService).toList())
                .build();
        return new GrpcServer(builder);
    }

    public GrpcServer start() {
        try {
            server.start();
            logger.info("Server started");
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void await() {
        try {
            server.awaitTermination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdownNow() {
        server.shutdownNow();
        logger.info("Server shut down");
    }

}
