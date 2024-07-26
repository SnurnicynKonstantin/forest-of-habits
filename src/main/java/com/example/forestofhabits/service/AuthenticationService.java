package com.example.forestofhabits.service;

import com.example.forestofhabits.config.jwt.JwtAuthentication;
import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.controller.dto.LoginRequestDto;
import com.example.forestofhabits.controller.dto.RegistrationDto;
import com.example.forestofhabits.controller.dto.UserInfoDto;
import com.example.forestofhabits.exception.AuthException;
import com.example.forestofhabits.mapper.ActionMapper;
import com.example.forestofhabits.mapper.JwtMapper;
import com.example.forestofhabits.model.Account;
import com.example.forestofhabits.repository.AccountRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;

    private final JwtProvider jwtProvider;

    private final JwtMapper jwtMapper;

    public AuthenticationService(JwtProvider jwtProvider, AccountRepository accountRepository) {
        this.jwtProvider = jwtProvider;
        this.accountRepository = accountRepository;
        this.jwtMapper = JwtMapper.INSTANCE;
    }

    public JwtResponseDto login(LoginRequestDto loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("Account doesn't exist"));

        if (!account.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthException("Incorrect password");
        }

        final String accessToken = jwtProvider.generateAccessToken(account);
        return jwtMapper.toDtoCustom(accessToken, account);
    }

    public JwtResponseDto registration(RegistrationDto registrationDto) {
        Optional<Account> account = accountRepository.findByEmail(registrationDto.getEmail());

        if(account.isPresent()) {
            throw new AuthException("Account with email = " + registrationDto.getEmail() + " already exist");
        }

        Account newAccount = Account.builder().email(registrationDto.getEmail())
                .password(registrationDto.getPassword()).name(registrationDto.getName()).build();

        accountRepository.save(newAccount);

        //TODO: Send notification with link to email

        final String accessToken = jwtProvider.generateAccessToken(newAccount);
        return jwtMapper.toDtoCustom(accessToken, newAccount);
    }

    public UserInfoDto getUserInfo() {
        JwtAuthentication userInfo = Util.getAuthInfo();
        return UserInfoDto.builder().username(userInfo.getUserName()).email(userInfo.getEmail()).build();
    }
}
