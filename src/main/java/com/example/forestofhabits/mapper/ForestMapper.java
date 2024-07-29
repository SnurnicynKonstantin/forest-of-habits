package com.example.forestofhabits.mapper;

import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.model.Forest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface ForestMapper {
  ForestMapper INSTANCE = Mappers.getMapper( ForestMapper.class );

  ForestDto toDto(Forest forest);

  default ForestDto toDtoCustom(Forest forest) {
    return ForestDto.builder()
            .name(forest.getName())
            .id(forest.getId())
            .createdAt(forest.getCreatedAt())
            .totalNumberTrees(forest.getTrees().size())
            .build();
  }

  default ForestDto toDtoCustomWithTrees(Forest forest, Set<TreeDto> trees) {
    return ForestDto.builder()
            .name(forest.getName())
            .id(forest.getId())
            .createdAt(forest.getCreatedAt())
            .totalNumberTrees(forest.getTrees().size() - trees.size())
            .trees(trees)
            .build();
  }
}
