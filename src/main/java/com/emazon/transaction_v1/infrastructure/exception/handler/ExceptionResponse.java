package com.emazon.transaction_v1.infrastructure.exception.handler;

public enum ExceptionResponse {

    INVALID_TOKEN("Invalid or expired token"),

    ACCESS_DENIED("Access denied. You do not have sufficient privileges to access this resource."),

    INVALID_ITEM_ID("The given item id is invalid"),

    INVALID_QUANTITY("The given quantity is invalid"),

    STOCK_CLIENT_ERROR("Something went wrong with stock client");

    private String message;

    ExceptionResponse(String message) { this.message = message; }

    public String getMessage() { return message; }
}
