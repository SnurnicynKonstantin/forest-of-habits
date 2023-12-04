package com.example.forestofhabits.controller.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String name;
}
