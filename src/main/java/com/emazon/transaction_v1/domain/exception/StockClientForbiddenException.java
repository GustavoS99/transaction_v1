package com.emazon.transaction_v1.domain.exception;

public class StockClientForbiddenException extends StockClientException {
    public StockClientForbiddenException(String message) {
        super(message);
    }
}
