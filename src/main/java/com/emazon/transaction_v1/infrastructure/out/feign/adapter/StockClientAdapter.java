package com.emazon.transaction_v1.infrastructure.out.feign.adapter;

import com.emazon.transaction_v1.domain.exception.*;
import com.emazon.transaction_v1.domain.model.ItemUpdateQuantity;
import com.emazon.transaction_v1.domain.spi.IStockClientPort;
import com.emazon.transaction_v1.infrastructure.out.feign.client.StockFeignClient;
import com.emazon.transaction_v1.infrastructure.out.feign.interceptor.AuthRegistrationRequestInterceptor;
import feign.Feign;
import feign.FeignException;
import feign.http2client.Http2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static com.emazon.transaction_v1.infrastructure.out.feign.client.util.ClientPathDefinition.URL_STOCK_MICROSERVICE;
import static com.emazon.transaction_v1.util.GlobalConstants.END_JSON_RESPONSE;
import static com.emazon.transaction_v1.util.GlobalConstants.START_JSON_RESPONSE;

@RequiredArgsConstructor
public class StockClientAdapter implements IStockClientPort {

    private final AuthRegistrationRequestInterceptor authRegistrationRequestInterceptor;

    @Override
    public void increaseQuantity(ItemUpdateQuantity itemUpdateQuantity) {
        StockFeignClient stockFeignClient = Feign.builder()
                .client(new Http2Client())
                .requestInterceptor(authRegistrationRequestInterceptor)
                .target(StockFeignClient.class, URL_STOCK_MICROSERVICE);

        try {
            stockFeignClient.increaseQuantity(itemUpdateQuantity.getId(), itemUpdateQuantity.getQuantity());
        } catch (FeignException e) {
            if(e.status() == HttpStatus.BAD_REQUEST.value())
                throw new StockClientBadRequestException(getMessage(e));

            if(e.status() == HttpStatus.NOT_FOUND.value())
                throw new StockClientNotFoundException(getMessage(e));

            if(e.status() == HttpStatus.UNAUTHORIZED.value())
                throw new StockClientUnauthorizedException(getMessage(e));

            if(e.status() == HttpStatus.FORBIDDEN.value())
                throw new StockClientForbiddenException(getMessage(e));

            throw new StockClientException();
        }
    }

    private static String getMessage(FeignException e) {
        return e.contentUTF8()
                .substring(START_JSON_RESPONSE.length(), e.contentUTF8().length() - END_JSON_RESPONSE.length());
    }
}
