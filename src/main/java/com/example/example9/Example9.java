package com.example.example9;

import com.example.grpc.proto.status.T10;

public class Example9 {

    public static void main(String[] args) {
        T10 t10 = T10.newBuilder().build();
        System.out.println(t10.getId() == 0 ? "empty value" : t10.getId());

    }
}
