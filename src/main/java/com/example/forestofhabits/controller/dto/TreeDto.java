package com.example.forestofhabits.controller.dto;

import com.example.forestofhabits.enums.TreeType;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
public class TreeDto {
  private Long id;
  private Long forestId;
  private String name;
  private String description;
  private TreeType type;
  private ZonedDateTime createdAt;
  private int limit;
  private Set<ActionDto> actions;
}
