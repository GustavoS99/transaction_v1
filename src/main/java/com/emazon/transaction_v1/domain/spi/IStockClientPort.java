package com.emazon.transaction_v1.domain.spi;

import com.emazon.transaction_v1.domain.model.ItemUpdateQuantity;

public interface IStockClientPort {
    void increaseQuantity(ItemUpdateQuantity itemUpdateQuantity);
}
