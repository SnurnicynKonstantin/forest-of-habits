package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.TreeDto;
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
                            .description(tree.getDescription())
                            .type(tree.getType())
                            .createdAt(tree.getCreatedAt())
                            .topLimit(tree.getTopLimit())
                            .build())
            .toList();
  }

  public TreeDto createForest(TreeDto request) {
    Tree newTreet = Tree
            .builder()
            .name(request.getName())
            .description(request.getDescription())
            .type(request.getType())
            .forest(getForest(request.getForestId()))
            .topLimit(request.getTopLimit())
            .build();
    Tree createdTree = treeRepository.save(newTreet);
    return TreeDto
            .builder()
            .id(createdTree.getId())
            .name(createdTree.getName())
            .description(createdTree.getDescription())
            .type(createdTree.getType())
            .topLimit(createdTree.getTopLimit())
            .createdAt(createdTree.getCreatedAt())
            .build();
  }

  public TreeDto updateTree(TreeDto request, Long id) {
    Tree tree = treeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tree with id = " + id + " doesn't exist"));
    tree.setName(request.getName());
    tree.setDescription(request.getDescription());
    tree.setTopLimit(request.getTopLimit());
    Tree updatedTree = treeRepository.save(tree);
    return TreeDto
            .builder()
            .id(updatedTree.getId())
            .name(updatedTree.getName())
            .description(updatedTree.getDescription())
            .type(updatedTree.getType())
            .topLimit(updatedTree.getTopLimit())
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
