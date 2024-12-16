package com.example.converter;

import com.example.model.User;
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

}
