package com.example.forestofhabits.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class HabitDto {
    private UUID id;
    private String name;
    private Integer counts;
    private Date lastSelect;
}
