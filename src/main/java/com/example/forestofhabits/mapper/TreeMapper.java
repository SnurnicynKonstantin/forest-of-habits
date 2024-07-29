package com.example.forestofhabits.mapper;

import com.example.forestofhabits.controller.dto.TreeDto;
import com.example.forestofhabits.model.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface TreeMapper {
  TreeMapper INSTANCE = Mappers.getMapper( TreeMapper.class );

  @Mapping(source = "limitActionCount", target = "limit")
  TreeDto toDto(Tree tree);

  @Mapping(source = "limitActionCount", target = "limit")
  Set<TreeDto> toDtoList(Set<Tree> tree);

  default TreeDto toDtoCustom(Tree tree, Long forestId) {
    return TreeDto.builder()
            .name(tree.getName())
            .id(tree.getId())
            .forestId(forestId)
            .description(tree.getDescription())
            .type(tree.getType())
            .createdAt(tree.getCreatedAt())
            .limit(tree.getLimitActionCount())
            .build();
  }
}
