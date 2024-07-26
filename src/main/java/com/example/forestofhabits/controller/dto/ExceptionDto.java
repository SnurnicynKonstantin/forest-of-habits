package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
  private String message;
}
