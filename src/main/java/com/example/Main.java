package com.example;

import com.example.repository.AccountRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.server.GrpcServer;
import com.example.service.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        GrpcServer.create(List.of(new UserService(new UserRepository()),
                new TransferService(new AccountRepository()),
                new FlowControlService(),
                new GuessService(),
                new ProductService(new ProductRepository()))).start().await();
    }

}