package com.emazon.transaction_v1.infrastructure.out.jpa.adapter;

import com.emazon.transaction_v1.domain.model.Supply;
import com.emazon.transaction_v1.domain.spi.ISupplyPersistencePort;
import com.emazon.transaction_v1.infrastructure.out.jpa.mapper.SupplyEntityMapper;
import com.emazon.transaction_v1.infrastructure.out.jpa.repository.ISupplyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SupplyJpaAdapter implements ISupplyPersistencePort {

    private final ISupplyRepository supplyRepository;

    private final SupplyEntityMapper supplyEntityMapper;

    @Override
    public Supply save(Supply supply) {
        return supplyEntityMapper.toSupply(
                supplyRepository.save(supplyEntityMapper.toSupplyEntity(supply)));
    }
}
