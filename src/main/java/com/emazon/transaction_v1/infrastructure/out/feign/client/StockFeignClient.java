package com.emazon.transaction_v1.infrastructure.out.feign.client;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import static com.emazon.transaction_v1.infrastructure.out.feign.client.util.ClientPathDefinition.ITEMS_INCREASE_QUANTITY;
import static com.emazon.transaction_v1.util.GlobalConstants.*;

public interface StockFeignClient {

    @RequestLine(ITEMS_INCREASE_QUANTITY)
    @Headers(CONTENT_TYPE_APPLICATION_JSON)
    @Body(OPEN_CURLY_BRACES + INCREASE_ITEM_QUANTITY_JSON_TEMPLATE + CLOSE_CURLY_BRACES)
    void increaseQuantity(@Param(ID) Long id, @Param(QUANTITY) Long quantity);
}
