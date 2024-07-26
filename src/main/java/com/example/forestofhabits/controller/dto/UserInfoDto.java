package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoDto {
  String username;
  String email;
}
