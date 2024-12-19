package com.example.converter;

import com.example.model.Account;
import com.example.model.User;
import com.grpc.service.AccountBalance;
import com.grpc.service.Role;
import com.grpc.service.UserResponse;

public final class ProtoConverter {

    private ProtoConverter() {

    }

    public static UserResponse userModelToProtoModel(User user) {
        return UserResponse.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setRole(Role.valueOf(user.getRole().name()))
                .build();
    }

    public static AccountBalance accountModelToProtoModel(Account account) {
        return AccountBalance.newBuilder().setAccountNumber(account.getId()).setAmount(account.getAmount()).build();
    }

}
