package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.service.TreeService;
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
@RequestMapping("/tree")
public class TreeController {
  private final TreeService treeService;

  public TreeController(TreeService treeService) {
    this.treeService = treeService;
  }

  @GetMapping("/byForest/{forestId}")
  public ResponseEntity<List<TreeDto>> getListOfTreesByForest(@PathVariable Long forestId) {
    return ResponseEntity.ok(treeService.getListOfTrees(forestId));
  }

  @PostMapping
  public ResponseEntity<TreeDto> createTree(@RequestBody TreeDto request) {
    return ResponseEntity.ok(treeService.createTree(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TreeDto> updateTree(@RequestBody TreeDto request, @PathVariable Long id) {
    return ResponseEntity.ok(treeService.updateTree(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTree(@PathVariable Long id) {
    treeService.deleteTree(id);
    return ResponseEntity.ok().build();
  }
}
