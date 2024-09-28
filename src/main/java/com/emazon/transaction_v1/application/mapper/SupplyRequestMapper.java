package com.emazon.transaction_v1.application.mapper;

import com.emazon.transaction_v1.application.dto.SupplyRequest;
import com.emazon.transaction_v1.domain.model.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplyRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "addedAt", ignore = true)
    @Mapping(target = "nextSupplyAt", ignore = true)
    Supply toSupply(SupplyRequest supplyRequest);
}
