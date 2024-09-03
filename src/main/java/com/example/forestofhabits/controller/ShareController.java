package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.enums.TreeStatus;
import com.example.forestofhabits.service.ForestService;
import com.example.forestofhabits.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shared")
public class ShareController {
  private final ForestService forestService;
  private final TreeService treeService;

  public ShareController(ForestService forestService, TreeService treeService) {
    this.forestService = forestService;
    this.treeService = treeService;
  }

  @GetMapping("/{id}")
  ResponseEntity<ForestDto> getForest(@PathVariable UUID id) {
    return ResponseEntity.ok(forestService.getByUuid(id));
  }

  @GetMapping("/tree/{id}")
  ResponseEntity<TreeDto> getTree(@PathVariable Long id) {
    return ResponseEntity.ok(treeService.getById(id));
  }

  @GetMapping("/by_forest/{id}")
  public ResponseEntity<List<TreeDto>> getTreesByForestId(
          @PathVariable UUID id,
          @RequestParam(required = false, defaultValue = "ALL") TreeStatus status) {
    return ResponseEntity.ok(treeService.getListOfSharedTrees(id, status));
  }
}
