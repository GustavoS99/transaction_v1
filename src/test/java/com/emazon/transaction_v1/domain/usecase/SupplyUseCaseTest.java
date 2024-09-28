package com.emazon.transaction_v1.domain.usecase;

import com.emazon.transaction_v1.domain.exception.*;
import com.emazon.transaction_v1.domain.model.Supply;
import com.emazon.transaction_v1.domain.spi.IStockClientPort;
import com.emazon.transaction_v1.domain.spi.ISupplyPersistencePort;
import com.emazon.transaction_v1.domain.spi.ITransactionAuthenticationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplyUseCaseTest {

    @Mock
    private ISupplyPersistencePort supplyPersistencePort;

    @Mock
    private ITransactionAuthenticationPort transactionAuthenticationPort;

    @Mock
    private IStockClientPort stockClientPort;

    @InjectMocks
    private SupplyUseCase supplyUseCase;

    private Supply supplyInput;
    private Supply expectedSupply;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 3L;
        supplyInput = Supply.builder()
                .userId(userId)
                .itemId(1L)
                .quantity(30L)
                .build();

        expectedSupply = Supply.builder()
                .id(1L)
                .userId(supplyInput.getUserId())
                .itemId(supplyInput.getItemId())
                .quantity(supplyInput.getQuantity())
                .build();
    }

    @Test
    void when_addSupply_expect_successCallToPersistenceAndStockMicroservice() {

        when(transactionAuthenticationPort.getUserId()).thenReturn(userId);

        when(supplyPersistencePort.save(any(Supply.class))).thenReturn(expectedSupply);

        doNothing().when(stockClientPort).increaseQuantity(any());

        supplyUseCase.add(supplyInput);

        verify(supplyPersistencePort, times(1)).save(any(Supply.class));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "-2"
    }, nullValues = {"null"})
    void expect_InvalidQuantityException_when_quantityInputIsWrong(Long quantity) {
        supplyInput.setQuantity(quantity);

        assertThrows(InvalidQuantityException.class, () -> supplyUseCase.add(supplyInput));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "null",
            "-2"
    }, nullValues = {"null"})
    void expect_InvalidItemIdException_when_itemIdInputIsWrong(Long itemId) {
        supplyInput.setItemId(itemId);

        assertThrows(InvalidItemIdException.class, () -> supplyUseCase.add(supplyInput));
    }

    private static Stream<Arguments> providedExpect_StockClientException_when_stockClientFails() {
        return Stream.of(
                Arguments.of(StockClientException.class),

                Arguments.of(StockClientBadRequestException.class),

                Arguments.of(StockClientNotFoundException.class),

                Arguments.of(StockClientUnauthorizedException.class),

                Arguments.of(StockClientForbiddenException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("providedExpect_StockClientException_when_stockClientFails")
    void expect_StockClientException_when_stockClientFails(Class<StockClientException> toBeThrown) {

        when(transactionAuthenticationPort.getUserId()).thenReturn(userId);

        when(supplyPersistencePort.save(any(Supply.class))).thenReturn(expectedSupply);

        doThrow(toBeThrown).when(stockClientPort).increaseQuantity(any());

        assertThrows(toBeThrown, () -> supplyUseCase.add(supplyInput));
    }
}