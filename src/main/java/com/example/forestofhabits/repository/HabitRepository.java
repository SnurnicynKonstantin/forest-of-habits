package com.example.forestofhabits.repository;

import com.example.forestofhabits.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HabitRepository  extends JpaRepository<Habit, UUID> {
    List<Habit> findByAccountId(UUID accountId);
}
