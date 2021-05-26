package com.ptit.service.service.impl;

import com.ptit.service.model.entity.User;
import com.ptit.service.model.exception.TypeCustomException;
import com.ptit.service.model.request.LoginRequest;
import com.ptit.service.model.request.RegisterRequest;
import com.ptit.service.model.response.UserInfoResponse;
import com.ptit.service.repository.UserRepository;
import com.ptit.service.security.JWTUtils;
import com.ptit.service.service.UserService;
import com.ptit.service.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    @Override
    public UserInfoResponse register(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(registerRequest.getUsername());
        if (user != null) {
            throw TypeCustomException.USER_NAME_EXISTED.getDefault().get();
        }
        User newUser = UserMapper.mapRequest2User(registerRequest);
        String token;
        newUser = userRepository.save(newUser);
        try {
            token = jwtUtils.generateToken(newUser);
        } catch (Exception e) {
            log.error(e);
            throw TypeCustomException.USER_REGISTER_ERROR.getDefault().get();
        }
        try {
            return UserInfoResponse.builder()
                    .token(token)
                    .username(newUser.getUsername())
                    .phoneNumber(newUser.getPhoneNumber())
                    .build();
        } catch (Exception e) {
            log.error(e);
            throw TypeCustomException.USER_REGISTER_ERROR.getDefault().get();
        }
    }

    @Override
    public UserInfoResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw TypeCustomException.USER_NOT_FOUND.getDefault().get();
        }
        String token = jwtUtils.generateToken(user);
        return UserInfoResponse.builder()
                .token(token)
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

}
