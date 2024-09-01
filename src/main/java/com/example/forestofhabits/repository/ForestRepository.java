package com.example.forestofhabits.repository;

import com.example.forestofhabits.model.Forest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForestRepository extends JpaRepository<Forest, Long> {
  List<Forest> findByAccountId(Long accountId);

  Optional<Forest> getByIdAndAccountId(Long forestId, Long accountId);
}
