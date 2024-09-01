package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.service.ForestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/forest")
public class ForestController {
  private final ForestService forestService;

  public ForestController(ForestService forestService) {
    this.forestService = forestService;
  }

  @GetMapping
  public ResponseEntity<List<ForestDto>> getListOfForests() {
    return ResponseEntity.ok(forestService.getListOfForests());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ForestDto> getById(@PathVariable Long id) {
    return ResponseEntity.ok(forestService.getById(id));
  }

  @PostMapping
  public ResponseEntity<ForestDto> createForest(@RequestBody ForestDto request) {
    return ResponseEntity.ok(forestService.createForest(request.getName()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ForestDto> updateForest(@RequestBody ForestDto request, @PathVariable Long id) {
    return ResponseEntity.ok(forestService.updateForest(request.getName(), id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteForest(@PathVariable Long id) {
    forestService.deleteForest(id);
    return ResponseEntity.ok().build();
  }

}
