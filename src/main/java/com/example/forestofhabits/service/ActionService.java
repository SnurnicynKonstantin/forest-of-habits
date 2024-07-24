package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.ActionDto;
import com.example.forestofhabits.exception.ValidateException;
import com.example.forestofhabits.model.Action;
import com.example.forestofhabits.model.Tree;
import com.example.forestofhabits.repository.ActionRepository;
import com.example.forestofhabits.repository.TreeRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ActionService {
  private final ActionRepository actionRepository;

  private final TreeRepository treeRepository;

  public ActionService(ActionRepository actionRepository, TreeRepository treeRepository) {
    this.actionRepository = actionRepository;
    this.treeRepository = treeRepository;
  }

  public List<ActionDto> getListOfActions(Long treeId) {
    return actionRepository
            .findByTreeId(treeId)
            .stream().map(action ->
                    ActionDto.builder()
                            .id(action.getId())
                            .treeId(treeId)
                            .comment(action.getComment())
                            .createdAt(action.getCreatedAt())
                            .build())
            .toList();
  }

  public ActionDto createAction(ActionDto request) {
    Tree tree = getTree(request.getTreeId());

    if(tree.getActions().size() >= tree.getLimitActionCount()) {
      throw new ValidateException("Action limit has been reached");
    }

    Action newAction = Action
            .builder()
            .comment(request.getComment())
            .tree(getTree(request.getTreeId()))
            .build();
    Action createdAction = actionRepository.save(newAction);
    return ActionDto
            .builder()
            .id(createdAction.getId())
            .treeId(request.getTreeId())
            .comment(createdAction.getComment())
            .createdAt(createdAction.getCreatedAt())
            .build();
  }

  public void deleteAction(Long id) {
    actionRepository.deleteById(id);
  }

  private Tree getTree(Long treeId) {
    return treeRepository.findById(treeId)
            .orElseThrow(() -> new EntityNotFoundException("Tree with id " + treeId + " not found"));

  }
}
