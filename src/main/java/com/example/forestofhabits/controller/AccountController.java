package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.StatDto;
import com.example.forestofhabits.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final StatService statService;

    public AccountController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/stat")
    public ResponseEntity<StatDto> getStat() {
        return ResponseEntity.ok(statService.getStat());
    }
}
