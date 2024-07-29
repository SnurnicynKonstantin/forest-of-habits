package com.example.forestofhabits.repository;

import com.example.forestofhabits.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository  extends JpaRepository<Tree, Long> {
  List<Tree> findByForestAccountIdAndForestId(Long accountId, Long forestId);
}
