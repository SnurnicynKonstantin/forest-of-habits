package com.example.forestofhabits.mapper;

import com.example.forestofhabits.controller.dto.ActionDto;
import com.example.forestofhabits.model.Action;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ActionMapper {
  ActionMapper INSTANCE = Mappers.getMapper( ActionMapper.class );

  ActionDto toDto(Action action);

  Set<ActionDto> toDto(Set<Action> action);

  default ActionDto toDtoCustom(Action action, Long treeId) {
    return ActionDto.builder()
            .id(action.getId())
            .treeId(treeId)
            .comment(action.getComment())
            .createdAt(action.getCreatedAt())
            .build();
  }
}
