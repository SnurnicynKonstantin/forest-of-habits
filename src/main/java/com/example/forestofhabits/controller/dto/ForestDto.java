package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ForestDto {
  private Long id;
  private String name;
  private ZonedDateTime createdAt;
//  private List<TreeResponse> trees;
  private Integer totalNumberTrees;
  //TODO: Implement shared
//  private UUID sharedId;
}
