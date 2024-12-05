package com.example.example5;

import com.example.grpc.proto.composition.Order;
import com.example.grpc.proto.composition.OrderStatus;
import com.example.grpc.proto.composition.Product;

public class Composition5 {

    public static void main(String[] args) {
        Product product = Product.newBuilder().setName("dota-2").setPrice(500).build();
        OrderStatus orderStatus = OrderStatus.PENDING;

        Order order = Order.newBuilder().setId(1).setOrderStatus(orderStatus).setProduct(product).build();
        System.out.println(order);
    }
}
