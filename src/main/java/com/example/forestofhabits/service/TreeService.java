package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.enums.TreeType;
import com.example.forestofhabits.model.Forest;
import com.example.forestofhabits.model.Tree;
import com.example.forestofhabits.repository.ForestRepository;
import com.example.forestofhabits.repository.TreeRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TreeService {
  private final TreeRepository treeRepository;
  private final ForestRepository forestRepository;

  public TreeService(TreeRepository treeRepository, ForestRepository forestRepository) {
    this.treeRepository = treeRepository;
    this.forestRepository = forestRepository;
  }

  public List<TreeDto> getListOfTrees(Long forestId) {
    return treeRepository
            .findByForestAccountIdAndForestId(Util.getAuthInfo().getAccountId(), forestId)
            .stream().map(tree ->
                    TreeDto.builder()
                            .name(tree.getName())
                            .id(tree.getId())
                            .forestId(forestId)
                            .description(tree.getDescription())
                            .type(tree.getType())
                            .createdAt(tree.getCreatedAt())
                            .limit(tree.getLimitActionCount())
                            .build())
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
            .build();
    Tree createdTree = treeRepository.save(newTreet);
    return TreeDto
            .builder()
            .id(createdTree.getId())
            .forestId(request.getForestId())
            .name(createdTree.getName())
            .description(createdTree.getDescription())
            .type(createdTree.getType())
            .limit(createdTree.getLimitActionCount())
            .createdAt(createdTree.getCreatedAt())
            .build();
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
    return TreeDto
            .builder()
            .id(updatedTree.getId())
            .name(updatedTree.getName())
            .description(updatedTree.getDescription())
            .type(updatedTree.getType())
            .limit(updatedTree.getLimitActionCount())
            .createdAt(updatedTree.getCreatedAt())
            .build();
  }

  public void deleteTree(Long id) {
    treeRepository.deleteById(id);
  }

  private Forest getForest(Long forestId) {
    return forestRepository.findById(forestId)
            .orElseThrow(() -> new EntityNotFoundException("Forest with id " + forestId + " not found"));

  }
}
