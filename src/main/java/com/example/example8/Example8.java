package com.example.example8;

import com.example.grpc.proto.mapList.T1;
import com.example.grpc.proto.mapList.T2;
import com.example.grpc.proto.mapList.T3;

import java.util.List;

public class Example8 {

    public static void main(String[] args) {

        T2 t2 = T2.newBuilder().addAllList(List.of(T3.newBuilder().setName("123").setYear(2000).build(), T3.newBuilder().setName("123").setYear(2000).build())).build();
        T1 t1 = T1.newBuilder().putM(1L, t2).build();


        System.out.println(t1);
    }
}
