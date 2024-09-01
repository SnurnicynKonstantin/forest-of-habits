package com.example.forestofhabits.repository;

import com.example.forestofhabits.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
  List<Action> findByTreeId(Long treeId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM action WHERE id = (SELECT id FROM action WHERE CAST(created_at AS date) = CAST(?1 AS date) ORDER BY created_at DESC LIMIT 1)",
  nativeQuery = true)
  void deleteLatestActionByDay(ZonedDateTime createdAt);
}
