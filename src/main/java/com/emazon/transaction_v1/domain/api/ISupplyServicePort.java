package com.emazon.transaction_v1.domain.api;

import com.emazon.transaction_v1.domain.model.Supply;

public interface ISupplyServicePort {
    void add(Supply supply);
}
