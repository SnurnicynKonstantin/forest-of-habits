package com.example.forestofhabits.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Account {
    private UUID id;
    private String name;
    private String password;
}
