package com.example.example11;

import com.google.protobuf.BoolValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import org.example.grpc.proto.wrapper.Wrapper;

public class WrapperExample {

    public static void main(String[] args) {
        Wrapper wrapper1 = Wrapper.newBuilder().setWBool(BoolValue.newBuilder().setValue(true).build()).setWStr(StringValue.newBuilder().setValue("asd").build()).setWDouble(DoubleValue.of(500)).build();
        Wrapper wrapper2 = Wrapper.newBuilder().setWStr(StringValue.newBuilder().setValue("value").build()).setTimeStamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis()).build()).build();

        System.out.println(wrapper1);
        System.out.println(wrapper2);
    }

}
