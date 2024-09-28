package com.emazon.transaction_v1.application.handler;

import com.emazon.transaction_v1.application.dto.SupplyRequest;
import com.emazon.transaction_v1.application.mapper.SupplyRequestMapper;
import com.emazon.transaction_v1.domain.api.ISupplyServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplyHandler implements ISupplyHandler {

    private final ISupplyServicePort supplyServicePort;
    private final SupplyRequestMapper supplyRequestMapper;

    @Override
    public void addSupply(SupplyRequest supplyRequest) {
        supplyServicePort.add(supplyRequestMapper.toSupply(supplyRequest));
    }
}
