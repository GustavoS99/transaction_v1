package com.emazon.transaction_v1.application.handler;

import com.emazon.transaction_v1.application.dto.SupplyRequest;
import com.emazon.transaction_v1.application.mapper.SupplyRequestMapper;
import com.emazon.transaction_v1.application.mapper.SupplyRequestMapperImpl;
import com.emazon.transaction_v1.domain.api.ISupplyServicePort;
import com.emazon.transaction_v1.domain.model.Supply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplyHandlerTest {

    @Mock
    private ISupplyServicePort supplyServicePort;

    @Mock
    private SupplyRequestMapperImpl supplyRequestMapper;

    @InjectMocks
    private SupplyHandler supplyHandler;
    
    private SupplyRequest supplyRequest;

    @BeforeEach
    void setUp() {
        supplyRequest = SupplyRequest.builder()
                .itemId(1L)
                .quantity(30L)
                .build();
    }

    @Test
    void when_addSupply_expect_callToServicePort() {
        doCallRealMethod().when(supplyRequestMapper).toSupply(any(SupplyRequest.class));

        supplyHandler.addSupply(supplyRequest);

        verify(supplyServicePort, times(1)).add(any(Supply.class));
    }
}