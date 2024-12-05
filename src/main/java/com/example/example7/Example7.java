package com.example.example7;

import com.example.grpc.proto.map.Car;
import com.example.grpc.proto.map.Dealer;

public class Example7 {

    public static void main(String[] args) {
        Car car1 = Car.newBuilder().setId(1L).setModel("BMW-1").setYear(2000).build();
        Car car2 = Car.newBuilder().setId(2L).setModel("BMW-2").setYear(2001).build();
        Car car3 = Car.newBuilder().setId(3L).setModel("BMW-3").setYear(2002).build();
        Car car4 = Car.newBuilder().setId(4L).setModel("BMW-3").setYear(2002).build();

        Dealer dealer = Dealer.newBuilder()
                .putInventory(car1.getId(), car1)
                .putInventory(car2.getId(), car2)
                .putInventory(car3.getId(), car3)
                .build();



    }

}
