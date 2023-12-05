package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.HabitDto;
import com.example.forestofhabits.model.Account;
import com.example.forestofhabits.model.Count;
import com.example.forestofhabits.model.Habit;
import com.example.forestofhabits.repository.AccountRepository;
import com.example.forestofhabits.repository.CountRepository;
import com.example.forestofhabits.repository.HabitRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class HabitService {
    private final HabitRepository habitRepository;

    private final AccountRepository accountRepository;

    private final CountRepository countRepository;

    public HabitService(HabitRepository habitRepository, AccountRepository accountRepository, CountRepository countRepository) {
        this.habitRepository = habitRepository;
        this.accountRepository = accountRepository;
        this.countRepository = countRepository;
    }

    public void createHabit(String name) {
        Habit newHabit = Habit.builder().name(name).account(getCurrentAccount()).build();
        habitRepository.save(newHabit);
    }

    public void selectHabit(UUID habitId) {
        Habit habit = getCurrentAccount().getHabits()
                .stream().filter(item -> item.getId().equals(habitId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Habit with id " + habitId + " isn't related to your account."));

        Count newCount = Count.builder().habit(habit).build();
        countRepository.save(newCount);
    }

    public List<HabitDto> getListOfHabits() {
        return getCurrentAccount().getHabits().stream().map(habit -> //TODO: Replace on view with join
                        HabitDto.builder()
                                .name(habit.getName())
                                .id(habit.getId())
                                .counts(habit.getCounts().size())
                                .lastSelect(habit.getCounts().stream().findFirst().get().getCreatedTime())
                                .build())
                .toList();
    }

    private Account getCurrentAccount() {
        UUID accountId = UUID.fromString(Util.getAuthInfo().getAccountId());
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + accountId + " not found"));

    }
}
