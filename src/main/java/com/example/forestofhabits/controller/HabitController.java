package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.CreateHabitDto;
import com.example.forestofhabits.controller.dto.HabitDto;
import com.example.forestofhabits.controller.dto.SelectHabitDto;
import com.example.forestofhabits.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/habit")
public class HabitController {

//    private final HabitService habitService;
//
//    public HabitController(HabitService habitService) {
//        this.habitService = habitService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<HabitDto>> getListOfHabits() {
//        return ResponseEntity.ok(habitService.getListOfHabits());
//    }
//
//    @PostMapping
//    public ResponseEntity<CreateHabitDto> createHabit(@RequestBody CreateHabitDto request) {
//        habitService.createHabit(request.getName());
//        return ResponseEntity.ok(request);
//    }
//
//    @PostMapping("/select")
//    public ResponseEntity<?> selectHabit(@RequestBody SelectHabitDto request) {
//        habitService.selectHabit(request.getHabitId());
//        return ResponseEntity.ok().build();
//    }
}
//1. List of habit current account (name, count of clicks)
//2. Get one habit. Name, list of dates
//3. Click on habit