package com.ptit.service.controller;

import com.ptit.service.common.BaseResponse;
import com.ptit.service.model.request.LoginRequest;
import com.ptit.service.model.request.RegisterRequest;
import com.ptit.service.service.MessageService;
import com.ptit.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final MessageService messageService;

    @PostMapping("/register")
    public BaseResponse registerUser(HttpServletRequest request, @Valid @RequestBody RegisterRequest registerRequest) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userService.register(registerRequest));
        baseResponse.setMessage(messageService.getMessage("user.register.success", request));
        return baseResponse;
    }

    @PostMapping("/login")
    public BaseResponse login(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userService.login(loginRequest));
        baseResponse.setMessage(messageService.getMessage("user.login.success", request));
        return baseResponse;
    }
}
