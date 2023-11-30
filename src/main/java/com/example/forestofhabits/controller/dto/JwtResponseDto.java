package com.example.forestofhabits.controller.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    private final String type = "Bearer";
    private String accessToken;
}
