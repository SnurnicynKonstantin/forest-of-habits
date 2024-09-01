package com.example.forestofhabits.mapper;

import com.example.forestofhabits.controller.dto.JwtResponseDto;
import com.example.forestofhabits.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JwtMapper {
  JwtMapper INSTANCE = Mappers.getMapper( JwtMapper.class );

  default JwtResponseDto toDtoCustom(String token, Account account) {
    return JwtResponseDto.builder()
            .token(token)
            .name(account.getName())
            .email(account.getEmail())
            .build();
  }
}
