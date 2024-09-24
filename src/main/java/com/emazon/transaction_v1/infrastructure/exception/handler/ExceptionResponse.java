package com.emazon.transaction_v1.infrastructure.exception.handler;

public enum ExceptionResponse {

    INVALID_TOKEN("Invalid or expired token"),

    ACCESS_DENIED("Access denied. You do not have sufficient privileges to access this resource."),;

    private String message;

    ExceptionResponse(String message) { this.message = message; }

    public String getMessage() { return message; }
}
