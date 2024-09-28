package com.emazon.transaction_v1.infrastructure.exception.handler;

import com.emazon.transaction_v1.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(InvalidItemIdException.class)
    public ResponseEntity<Map<String, String>> handleInvalidItemIdException(
            InvalidItemIdException invalidItemIdException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_ITEM_ID.getMessage()));
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<Map<String, String>> handleInvalidQuantityException(
            InvalidQuantityException invalidQuantityException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_QUANTITY.getMessage()));
    }

    @ExceptionHandler(StockClientException.class)
    public ResponseEntity<Map<String, String>> handleStockClientException(
            StockClientException stockClientException
    ) {
        if(stockClientException instanceof StockClientBadRequestException)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap(MESSAGE, stockClientException.getMessage()));

        if(stockClientException instanceof StockClientNotFoundException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap(MESSAGE, stockClientException.getMessage()));

        if(stockClientException instanceof StockClientUnauthorizedException)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap(MESSAGE, stockClientException.getMessage()));

        if(stockClientException instanceof StockClientForbiddenException)
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap(MESSAGE, stockClientException.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.STOCK_CLIENT_ERROR.getMessage()));
    }
}
