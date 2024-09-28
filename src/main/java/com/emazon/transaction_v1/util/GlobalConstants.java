package com.emazon.transaction_v1.util;

public class GlobalConstants {

    public static final String WAREHOUSE_WORKER = "WAREHOUSE_WORKER";

    public static final String CUSTOMER = "CUSTOMER";

    public static final String BEARER = "Bearer ";

    public static final int DAYS_UNTIL_NEXT_SUPPLY = 15;
    public static final int MIN_ID = 1;
    public static final int MIN_QUANTITY = 1;
    public static final String CLOSE_CURLY_BRACES = "%7D";
    public static final String OPEN_CURLY_BRACES = "%7B";
    public static final String INCREASE_ITEM_QUANTITY_JSON_TEMPLATE = "\"id\" : {id}, \"quantity\" : {quantity}";
    public static final String ID = "id";
    public static final String QUANTITY = "quantity";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    public static final String START_JSON_RESPONSE = "{\"Message\":\"";
    public static final String END_JSON_RESPONSE = "\"}";
}
