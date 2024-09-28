package com.emazon.transaction_v1.domain.spi;

import com.emazon.transaction_v1.domain.model.Supply;

public interface ISupplyPersistencePort {
    Supply save(Supply supply);
}
