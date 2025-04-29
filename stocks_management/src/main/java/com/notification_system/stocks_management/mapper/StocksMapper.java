package com.notification_system.stocks_management.mapper;

import com.notification_system.stocks_management.dto.StocksDto;
import com.notification_system.stocks_management.entity.Stocks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StocksMapper {

    Stocks toEntity(StocksDto dto);

    StocksDto toDto(Stocks stocks);
}
