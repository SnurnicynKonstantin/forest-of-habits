package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.ActionDto;
import com.example.forestofhabits.exception.ValidateException;
import com.example.forestofhabits.mapper.ActionMapper;
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

  private final ActionMapper actionMapper;

  public ActionService(ActionRepository actionRepository, TreeRepository treeRepository) {
    this.actionRepository = actionRepository;
    this.treeRepository = treeRepository;
    this.actionMapper = ActionMapper.INSTANCE;
  }

  public List<ActionDto> getListOfActions(Long treeId) {
    return actionRepository
            .findByTreeId(treeId)
            .stream().map(action -> actionMapper.toDtoCustom(action, treeId))
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
    return actionMapper.toDtoCustom(createdAction, request.getTreeId());
  }

  public void deleteAction(Long id) {
    actionRepository.deleteById(id);
  }

  private Tree getTree(Long treeId) {
    return treeRepository.findById(treeId)
            .orElseThrow(() -> new EntityNotFoundException("Tree with id " + treeId + " not found"));

  }
}
