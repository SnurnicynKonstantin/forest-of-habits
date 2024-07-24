package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.ActionDto;
import com.example.forestofhabits.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {
  private final ActionService actionService;

  public ActionController(ActionService actionService) {
    this.actionService = actionService;
  }

  @GetMapping("/byTree/{treeId}")
  public ResponseEntity<List<ActionDto>> getListOfActionsByTree(@PathVariable Long treeId) {
    return ResponseEntity.ok(actionService.getListOfActions(treeId));
  }

  @PostMapping
  public ResponseEntity<ActionDto> createAction(@RequestBody ActionDto request) {
    return ResponseEntity.ok(actionService.createAction(request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteAction(@PathVariable Long id) {
    actionService.deleteAction(id);
    return ResponseEntity.ok().build();
  }
}
