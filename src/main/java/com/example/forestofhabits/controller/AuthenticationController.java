package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.controller.dto.LoginRequestDto;
import com.example.forestofhabits.controller.dto.RegistrationDto;
import com.example.forestofhabits.controller.dto.UserInfoDto;
import com.example.forestofhabits.service.AuthenticationService;
import com.example.forestofhabits.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<JwtResponseDto> registration(@RequestBody RegistrationDto registrationRequest) {
        return ResponseEntity.ok(authenticationService.registration(registrationRequest));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        return ResponseEntity.ok(authenticationService.getUserInfo());
    }
}