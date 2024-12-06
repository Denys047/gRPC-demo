package com.example.example10;

import org.example.grpc.proto.oneof.Authntication;
import org.example.grpc.proto.oneof.Email;
import org.example.grpc.proto.oneof.Phone;
import org.example.grpc.proto.oneof.User;

public class Example10 {

    public static void main(String[] args) {

        Authntication authntication = Authntication
                .newBuilder()
                .setEmail(Email.newBuilder().setValue("asd@gmai.com").build())
                .setPhone(Phone.newBuilder().setValue("1234567890").build())
                .build();
        User user = User.newBuilder().setPassword("asd").setUsername("user").setAuth(authntication).build();
        printAuth(user.getAuth());
    }

    public static void printAuth(Authntication authntication) {
        switch (authntication.getAuthCase()) {
            case EMAIL -> System.out.println("email -> " + authntication.getEmail());
            case PHONE -> System.out.println("phone -> " + authntication.getPhone());
        }
    }
}
