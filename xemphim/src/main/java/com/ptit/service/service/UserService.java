package com.ptit.service.service;

import com.ptit.service.model.request.LoginRequest;
import com.ptit.service.model.request.RegisterRequest;
import com.ptit.service.model.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse register(RegisterRequest registerRequest);

    UserInfoResponse login(LoginRequest loginRequest);
}
