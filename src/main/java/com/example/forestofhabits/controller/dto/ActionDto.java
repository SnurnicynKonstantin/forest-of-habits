package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ActionDto {
  private Long id;
  private Long treeId;
  private String comment;
  private ZonedDateTime createdAt;
}
