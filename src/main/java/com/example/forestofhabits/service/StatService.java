package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.StatDto;
import com.example.forestofhabits.model.Forest;
import com.example.forestofhabits.model.Tree;
import com.example.forestofhabits.repository.ForestRepository;
import com.example.forestofhabits.repository.TreeRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatService {
  private final ForestRepository forestRepository;
  private final TreeRepository treeRepository;

  public StatService(ForestRepository forestRepository, TreeRepository treeRepository) {
    this.forestRepository = forestRepository;
    this.treeRepository = treeRepository;
  }

  public StatDto getStat() {
    Long accountId = Util.getAuthInfo().getAccountId();
    List<Forest> forests = forestRepository.findByAccountId(accountId);
    int allForests = forests.size();
    int allTrees = 0;
    int openTrees = 0;
    int closeTrees = 0;
    int openForests = 0;
    int closeForests = 0;
    for (Forest forest : forests) {
      //TODO: Use Join
      List<Tree> trees = treeRepository.findByForestAccountIdAndForestId(accountId, forest.getId());
      int all = trees.size();
      int close = (int) trees.stream().filter(tree -> switch (tree.getType()) {
        case BOOLEAN -> tree.getActions().size() > 0;
        case INFINITE -> false;
        case LIMITED -> tree.getActions().size() >= tree.getLimitActionCount();
      }).count();
      if (all != close) {
        openForests++;
      } else {
        closeForests++;
      }
      allTrees += all;
      closeTrees += close;
      openTrees += all - close;
    }
    return new StatDto(allTrees, openTrees, closeTrees, allForests, openForests, closeForests);
  }
}
