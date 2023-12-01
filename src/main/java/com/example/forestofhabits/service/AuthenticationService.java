package com.example.forestofhabits.service;

import com.example.forestofhabits.config.jwt.JwtAuthentication;
import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.controller.dto.LoginRequestDto;
import com.example.forestofhabits.exception.AuthException;
import com.example.forestofhabits.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {
    private final JwtProvider jwtProvider;

    public AuthenticationService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public JwtResponseDto login(LoginRequestDto authRequest) {
//        final User user = userService.getByLogin(authRequest.getLogin())
//                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        Account account = new Account(UUID.randomUUID(), "Name", "1234");

        if (!account.getPassword().equals(authRequest.getPassword())) {
            throw new AuthException("Incorrect password");
        }

        final String accessToken = jwtProvider.generateAccessToken(account);
        return JwtResponseDto.builder().accessToken(accessToken).build();
    }
}
