package com.emazon.transaction_v1.application.handler;

import com.emazon.transaction_v1.application.dto.SupplyRequest;

public interface ISupplyHandler {

    void addSupply(SupplyRequest supplyRequest);
}
