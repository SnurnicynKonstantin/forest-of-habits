package com.example.forestofhabits.repository;

import com.example.forestofhabits.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
  List<Action> findByTreeId(Long treeId);
}
