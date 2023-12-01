package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDto {
    private final String type = "Bearer";
    private String accessToken;
}
