package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.ActionDto;
import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.enums.TreeStatus;
import com.example.forestofhabits.enums.TreeType;
import com.example.forestofhabits.mapper.ActionMapper;
import com.example.forestofhabits.mapper.TreeMapper;
import com.example.forestofhabits.model.Forest;
import com.example.forestofhabits.model.Tree;
import com.example.forestofhabits.repository.ForestRepository;
import com.example.forestofhabits.repository.TreeRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class TreeService {
  private final TreeRepository treeRepository;
  private final ForestRepository forestRepository;
  private final TreeMapper treeMapper;

  private final ActionMapper actionMapper;

  public TreeService(TreeRepository treeRepository, ForestRepository forestRepository) {
    this.treeRepository = treeRepository;
    this.forestRepository = forestRepository;
    this.treeMapper = TreeMapper.INSTANCE;
    this.actionMapper = ActionMapper.INSTANCE;
  }

  public TreeDto getById(Long treeId) {
    //TODO: Probably replase on getById + owner validation
    Tree tree = treeRepository.findById(treeId)
            .orElseThrow(() -> new EntityNotFoundException("Tree with id = " + treeId + " doesn't exist"));
    //TODO: Move get actions to mapper
    Set<ActionDto> actions= actionMapper.toDto(tree.getActions());
    return treeMapper.toDtoCustomWithActions(tree, actions);
  }

  public List<TreeDto> getListOfTrees(Long forestId, TreeStatus status) {
    return getFilteredTreesByStatus(forestId, status)
            .stream()
            .map(tree -> treeMapper.toDtoCustom(tree, forestId))
            .toList();
  }

  public TreeDto createTree(TreeDto request) {
    boolean isBoolean = request.getType() == TreeType.BOOLEAN;
    Tree newTreet = Tree
            .builder()
            .name(request.getName())
            .description(request.getDescription())
            .type(request.getType())
            .forest(getForest(request.getForestId()))
            .limitActionCount(isBoolean ? 1 : request.getLimit())
            .createdAt(request.getCreatedAt())
            .build();
    Tree createdTree = treeRepository.save(newTreet);
    return treeMapper.toDtoCustom(createdTree, request.getForestId());
  }

  public TreeDto updateTree(TreeDto request, Long id) {
    Tree tree = treeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tree with id = " + id + " doesn't exist"));

    if(request.getName() != null && !request.getName().isBlank()) {
      tree.setName(request.getName());
    }

    if(request.getDescription() != null && !request.getDescription().isBlank()) {
      tree.setDescription(request.getDescription());
    }

    Tree updatedTree = treeRepository.save(tree);
    return treeMapper.toDto(updatedTree);
  }

  public void deleteTree(Long id) {
    treeRepository.deleteById(id);
  }

  private Forest getForest(Long forestId) {
    return forestRepository.findById(forestId)
            .orElseThrow(() -> new EntityNotFoundException("Forest with id " + forestId + " not found"));

  }

  private  List<Tree> getFilteredTreesByStatus(Long forestId, TreeStatus status) {
    //TODO: Need receive all data (trees with actions), after filter it without additional requests
    List<Tree> trees = treeRepository.findByForestAccountIdAndForestId(Util.getAuthInfo().getAccountId(), forestId);

    return switch (status) {
      case ALL -> trees;
      case OPEN -> getOpenTrees(trees);
      case CLOSE -> getCloseTrees(trees);
    };
  }

  private List<Tree> getOpenTrees(List<Tree> trees) {
    return trees.stream()
            .filter(tree -> switch (tree.getType()) {
              case BOOLEAN -> tree.getActions().size() == 0;
              case INFINITE -> true;
              case LIMITED -> tree.getActions().size() < tree.getLimitActionCount();
            })
            .toList();
  }

  private List<Tree> getCloseTrees(List<Tree> trees) {
    return trees.stream()
            .filter(tree -> switch (tree.getType()) {
              case BOOLEAN -> tree.getActions().size() > 0;
              case INFINITE -> false;
              case LIMITED -> tree.getActions().size() >= tree.getLimitActionCount();
            })
            .toList();
  }
}
