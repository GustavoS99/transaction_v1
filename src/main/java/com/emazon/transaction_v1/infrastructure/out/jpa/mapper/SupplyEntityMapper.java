package com.emazon.transaction_v1.infrastructure.out.jpa.mapper;

import com.emazon.transaction_v1.domain.model.Supply;
import com.emazon.transaction_v1.infrastructure.out.jpa.entity.SupplyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplyEntityMapper {

    Supply toSupply(SupplyEntity supplyEntity);

    SupplyEntity toSupplyEntity(Supply supply);
}
