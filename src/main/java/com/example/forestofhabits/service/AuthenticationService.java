package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.controller.dto.LoginRequestDto;
import com.example.forestofhabits.controller.dto.RegistrationDto;
import com.example.forestofhabits.exception.AuthException;
import com.example.forestofhabits.model.Account;
import com.example.forestofhabits.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;

    private final JwtProvider jwtProvider;

    public AuthenticationService(JwtProvider jwtProvider, AccountRepository accountRepository) {
        this.jwtProvider = jwtProvider;
        this.accountRepository = accountRepository;
    }

    public JwtResponseDto login(LoginRequestDto loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException("Account doesn't exist"));

        if (!account.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthException("Incorrect password");
        }

        final String accessToken = jwtProvider.generateAccessToken(account);
        return JwtResponseDto.builder().accessToken(accessToken).build();
    }

    public JwtResponseDto registration(RegistrationDto registrationDto) {
        Optional<Account> account = accountRepository.findByEmail(registrationDto.getEmail());

        if(account.isPresent()) {
            throw new AuthException("Account with email = " + registrationDto.getEmail() + " already exist");
        }

        if(!registrationDto.getPassword().equals(registrationDto.getPasswordConfirmation())) {
            throw new AuthException("Password doesn't equals confirmation password");
        }

        Account newAccount = Account.builder().email(registrationDto.getEmail())
                .password(registrationDto.getPassword()).name(registrationDto.getName()).build();

        accountRepository.save(newAccount);

        //TODO: Send notification with link to email

        final String accessToken = jwtProvider.generateAccessToken(newAccount);
        return JwtResponseDto.builder().accessToken(accessToken).build();
    }
}
