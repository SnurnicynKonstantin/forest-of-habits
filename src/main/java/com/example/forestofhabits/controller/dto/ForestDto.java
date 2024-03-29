package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ForestDto {
  private Long id;
  private String name;
  private Date createdAt;
}
