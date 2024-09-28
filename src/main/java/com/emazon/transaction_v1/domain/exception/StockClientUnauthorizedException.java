package com.emazon.transaction_v1.domain.exception;

public class StockClientUnauthorizedException extends StockClientException {
    public StockClientUnauthorizedException(String message) {
        super(message);
    }
}
