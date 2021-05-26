package com.ptit.service.service.mapper;

import com.ptit.service.model.entity.User;
import com.ptit.service.model.request.RegisterRequest;

public class UserMapper {

    public static User mapRequest2User(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();
    }

}
