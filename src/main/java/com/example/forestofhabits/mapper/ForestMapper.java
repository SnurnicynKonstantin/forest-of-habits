package com.example.forestofhabits.mapper;

import com.example.forestofhabits.controller.dto.ForestDto;
import com.example.forestofhabits.model.Forest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ForestMapper {
  ForestMapper INSTANCE = Mappers.getMapper( ForestMapper.class );

  ForestDto toDto(Forest forest);
}
