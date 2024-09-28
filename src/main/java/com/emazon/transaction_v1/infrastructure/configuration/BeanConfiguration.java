package com.emazon.transaction_v1.infrastructure.configuration;

import com.emazon.transaction_v1.domain.api.ISupplyServicePort;
import com.emazon.transaction_v1.domain.spi.IStockClientPort;
import com.emazon.transaction_v1.domain.spi.ISupplyPersistencePort;
import com.emazon.transaction_v1.domain.spi.ITransactionAuthenticationPort;
import com.emazon.transaction_v1.domain.usecase.SupplyUseCase;
import com.emazon.transaction_v1.infrastructure.out.feign.adapter.StockClientAdapter;
import com.emazon.transaction_v1.infrastructure.out.feign.interceptor.AuthRegistrationRequestInterceptor;
import com.emazon.transaction_v1.infrastructure.out.jpa.adapter.SupplyJpaAdapter;
import com.emazon.transaction_v1.infrastructure.out.jpa.mapper.SupplyEntityMapper;
import com.emazon.transaction_v1.infrastructure.out.jpa.repository.ISupplyRepository;
import com.emazon.transaction_v1.infrastructure.out.jwt.adapter.TransactionAuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ISupplyRepository supplyRepository;

    private final SupplyEntityMapper supplyEntityMapper;

    @Bean
    ISupplyPersistencePort supplyPersistencePort() {
        return new SupplyJpaAdapter(supplyRepository, supplyEntityMapper);
    }

    @Bean
    ITransactionAuthenticationPort transactionAuthenticationPort() {
        return new TransactionAuthenticationAdapter();
    }

    @Bean
    IStockClientPort stockClientPort(
            AuthRegistrationRequestInterceptor authRegistrationRequestInterceptor
    ) {
        return new StockClientAdapter(authRegistrationRequestInterceptor);
    }

    @Bean
    ISupplyServicePort supplyServicePort(
            ISupplyPersistencePort supplyPersistencePort,

            ITransactionAuthenticationPort transactionAuthenticationPort,

            IStockClientPort stockClientPort
    ) {
        return new SupplyUseCase(supplyPersistencePort, transactionAuthenticationPort, stockClientPort);
    }
}
