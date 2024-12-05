package com.example.example4;

import com.example.grpc.proto.scalarTypes.ScalarTypes;

public class Example4 {

    public static void main(String[] args) {
        ScalarTypes scalarTypes = ScalarTypes.newBuilder()
                .setValueSint(32)
                .setValueInt32(-32)
                .setValueUint(123)
                .build();

        System.out.println(scalarTypes);
    }

}
