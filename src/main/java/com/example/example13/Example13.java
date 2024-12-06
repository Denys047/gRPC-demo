package com.example.example13;

import org.example.grpc.proto.types.V10;

import java.util.Arrays;

public class Example13 {
    public static void main(String[] args) {
        V10 v10 = V10.newBuilder().setV1(10).build();
        System.out.println(Arrays.toString(v10.toByteArray()));
        System.out.println(v10.toByteArray().length);
    }
    
}
