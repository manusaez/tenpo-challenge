package com.tenpo.challenge.service.mapper;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.entity.LogCallHistoryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogCallHistoryMapper {

    LogCallHistoryMapper INSTANCE = Mappers.getMapper(LogCallHistoryMapper.class);

    LogCallHistoryDto toDto(LogCallHistoryEntity logCallHistory);

    @Mapping(target = "id", ignore=true)
    LogCallHistoryEntity toEntity(LogCallHistoryDto logCallHistoryDto);
}
