package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.controller.dto.LoginRequestDto;
import com.example.forestofhabits.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";//greetingService.getGreeting();
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    public String registration() {
        return "";
    }
}

//1. Registration. User data (name, email, password) -> token, send to email link for confirmation
//2. Login.        User email and password -> token

//Registration:
//1. Endpoint for registration
//2. Validation of nick and email
//3. Add error classes
//4. Save data to DB
//5. Send notification to email for approve account
//6. Generate and return token

//Login:
//1. Endpoint for Login. Email and password
//2. Add validation of Email
//3. Create and return token

//Additional work:
//1. Create filter for listen tokens -> Add to security context
//2. Investigate Auth0 auth