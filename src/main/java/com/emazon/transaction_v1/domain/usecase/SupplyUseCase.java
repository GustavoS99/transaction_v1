package com.emazon.transaction_v1.domain.usecase;

import com.emazon.transaction_v1.domain.api.ISupplyServicePort;
import com.emazon.transaction_v1.domain.exception.*;
import com.emazon.transaction_v1.domain.model.ItemUpdateQuantity;
import com.emazon.transaction_v1.domain.model.Supply;
import com.emazon.transaction_v1.domain.spi.IStockClientPort;
import com.emazon.transaction_v1.domain.spi.ISupplyPersistencePort;
import com.emazon.transaction_v1.domain.spi.ITransactionAuthenticationPort;

import static com.emazon.transaction_v1.util.GlobalConstants.MIN_ID;
import static com.emazon.transaction_v1.util.GlobalConstants.MIN_QUANTITY;

public class SupplyUseCase implements ISupplyServicePort {

    private final ISupplyPersistencePort supplyPersistencePort;

    private final ITransactionAuthenticationPort transactionAuthenticationPort;

    private final IStockClientPort stockClientPort;

    public SupplyUseCase(
            ISupplyPersistencePort supplyPersistencePort,
            ITransactionAuthenticationPort transactionAuthenticationPort,
            IStockClientPort stockClientPort
    ) {
        this.supplyPersistencePort = supplyPersistencePort;

        this.transactionAuthenticationPort = transactionAuthenticationPort;

        this.stockClientPort = stockClientPort;
    }

    @Override
    public void add(Supply supply) {
        validateInput(supply);

        supply.setUserId(transactionAuthenticationPort.getUserId());

        supply = supplyPersistencePort.save(supply);

        increaseQuantityOnStock(supply);
    }

    private void increaseQuantityOnStock(Supply supply) {
        try {
            stockClientPort.increaseQuantity(ItemUpdateQuantity.builder()
                    .id(supply.getItemId())
                    .quantity(supply.getQuantity())
                    .build());
        } catch (StockClientNotFoundException e) {
            throw new StockClientNotFoundException(e.getMessage());

        } catch (StockClientUnauthorizedException e) {
            throw new StockClientUnauthorizedException(e.getMessage());

        } catch (StockClientBadRequestException e) {
            throw new StockClientBadRequestException(e.getMessage());

        } catch (StockClientForbiddenException e) {
            throw new StockClientForbiddenException(e.getMessage());

        } catch (StockClientException e) {
            throw new StockClientException();
        }
    }

    private void validateInput(Supply supply) {
        if(supply.getItemId() == null || supply.getItemId() < MIN_ID)
            throw new InvalidItemIdException();

        if(supply.getQuantity() == null || supply.getQuantity() < MIN_QUANTITY)
            throw new InvalidQuantityException();
    }
}
