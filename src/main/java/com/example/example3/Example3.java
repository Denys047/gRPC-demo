package com.example.example3;

import com.example.grpc.proto.user.v3.User;

public class Example3 {

    public static void main(String[] args) {
        //immutable{
        User user = createUser();
        User user1 = createUser();
        //}
        System.out.println(user1.equals(user));

    }


    public static User createUser() {
        return User.newBuilder().setId(-1).setFirstName("alex").build();
    }
}
