package com.example.forestofhabits.service;

import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.mapper.ForestMapper;
import com.example.forestofhabits.mapper.TreeMapper;
import com.example.forestofhabits.model.Account;
import com.example.forestofhabits.model.Forest;
import com.example.forestofhabits.repository.AccountRepository;
import com.example.forestofhabits.repository.ForestRepository;
import com.example.forestofhabits.util.Util;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ForestService {
    private final ForestRepository forestRepository;
    private final AccountRepository accountRepository;
    private final ForestMapper forestMapper;
    private final TreeMapper treeMapper;

    public ForestService(ForestRepository forestRepository, AccountRepository accountRepository) {
        this.forestRepository = forestRepository;
        this.accountRepository = accountRepository;
        this.forestMapper = ForestMapper.INSTANCE;
        this.treeMapper = TreeMapper.INSTANCE;
    }

    public List<ForestDto> getListOfForests() {
        return forestRepository //TODO: Replace on view with join
                .findByAccountId(Util.getAuthInfo().getAccountId())
                .stream()
                .map(forest -> {
                    return forestMapper.toDtoCustomWithTrees(
                            forest,
                            //TODO: Return last updated trees
                            treeMapper.toDtoList(forest.getTrees().stream().limit(3).collect(Collectors.toSet()))
                            );
                })
                .toList();
    }

    public ForestDto getById(Long forestId) {
        //TODO: Probably raplase on getById + owner validation
        Forest forest = forestRepository.getByIdAndAccountId(forestId, Util.getAuthInfo().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Forest with id = " + forestId + " doesn't exist"));
        return forestMapper.toDto(forest);
    }

    public ForestDto getByUuid(UUID id) {
        Forest forest = forestRepository.findByShareId(id)
                .orElseThrow(() -> new EntityNotFoundException("Forest with id = " + id + " doesn't exist"));
        return forestMapper.toDto(forest);
    }

    public ForestDto createForest(String forestName) {
        Forest newForest = Forest
                .builder()
                .name(forestName)
                .account(getCurrentAccount())
                .build();
        //TODO: Учесть дату создания дерева
        Forest createdForest = forestRepository.save(newForest);
        return forestMapper.toDto(createdForest);
    }

    public ForestDto updateForest(String forestName, Long forestId) {
        Forest forest = forestRepository.findById(forestId)
                .orElseThrow(() -> new EntityNotFoundException("Forest with id = " + forestId + " doesn't exist"));
        forest.setName(forestName);
        Forest updatedForest = forestRepository.save(forest);
        return forestMapper.toDto(updatedForest);
    }

    public UUID shareForest(Long forestId) {
        //TODO: Probably raplase on getById + owner validation
        Forest forest = forestRepository.getByIdAndAccountId(forestId, Util.getAuthInfo().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Forest with id = " + forestId + " doesn't exist"));
        forest.setShareId(UUID.randomUUID());
        return forestRepository.save(forest).getShareId();
    }

    public void unshareForest(Long forestId) {
        //TODO: Probably raplase on getById + owner validation
        Forest forest = forestRepository.getByIdAndAccountId(forestId, Util.getAuthInfo().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Forest with id = " + forestId + " doesn't exist"));
        forest.setShareId(null);
        forestRepository.save(forest);
    }

    public void deleteForest(Long forestId) {
        forestRepository.deleteById(forestId);
    }

    private Account getCurrentAccount() {
        Long accountId = Util.getAuthInfo().getAccountId();
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + accountId + " not found"));

    }
}
