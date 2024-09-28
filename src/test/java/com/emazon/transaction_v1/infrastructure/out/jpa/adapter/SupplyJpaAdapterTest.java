package com.emazon.transaction_v1.infrastructure.out.jpa.adapter;

import com.emazon.transaction_v1.domain.model.Supply;
import com.emazon.transaction_v1.infrastructure.out.jpa.entity.SupplyEntity;
import com.emazon.transaction_v1.infrastructure.out.jpa.mapper.SupplyEntityMapperImpl;
import com.emazon.transaction_v1.infrastructure.out.jpa.repository.ISupplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplyJpaAdapterTest {

    @Mock
    private ISupplyRepository supplyRepository;

    @Mock
    private SupplyEntityMapperImpl supplyEntityMapper;

    @InjectMocks
    private SupplyJpaAdapter supplyJpaAdapter;

    private Supply supply;
    private SupplyEntity supplyEntity;

    @BeforeEach
    void setUp() {
        supplyEntity = SupplyEntity.builder()
                .id(1L)
                .userId(3L)
                .itemId(1L)
                .quantity(30L)
                .addedAt(LocalDateTime.now())
                .nextSupplyAt(LocalDateTime.now().plus(Duration.ofDays(15)))
                .build();

        supply = Supply.builder()
                .userId(supplyEntity.getUserId())
                .itemId(supplyEntity.getItemId())
                .quantity(supplyEntity.getQuantity())
                .addedAt(supplyEntity.getAddedAt())
                .nextSupplyAt(supplyEntity.getNextSupplyAt())
                .build();
    }

    @Test
    void save() {
        doCallRealMethod().when(supplyEntityMapper).toSupplyEntity(any(Supply.class));

        when(supplyRepository.save(any(SupplyEntity.class))).thenReturn(supplyEntity);

        doCallRealMethod().when(supplyEntityMapper).toSupply(any(SupplyEntity.class));

        var result = supplyJpaAdapter.save(supply);

        assertNotNull(result);

        assertNotNull(result.getId());

        assertEquals(supply.getUserId(), result.getUserId());

        assertEquals(supply.getItemId(), result.getItemId());

        assertEquals(supply.getQuantity(), result.getQuantity());

        assertEquals(supply.getAddedAt(), result.getAddedAt());

        assertEquals(supply.getNextSupplyAt(), result.getNextSupplyAt());
    }
}