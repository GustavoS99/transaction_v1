package com.emazon.transaction_v1.domain.exception;

public class StockClientException extends RuntimeException {

    public StockClientException() { super(); }

    public StockClientException(String message) { super(message); }
}
