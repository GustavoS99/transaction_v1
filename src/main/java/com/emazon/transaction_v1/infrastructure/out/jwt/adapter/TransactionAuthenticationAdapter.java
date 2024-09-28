package com.emazon.transaction_v1.infrastructure.out.jwt.adapter;

import com.emazon.transaction_v1.domain.spi.ITransactionAuthenticationPort;
import org.springframework.security.core.context.SecurityContextHolder;

public class TransactionAuthenticationAdapter implements ITransactionAuthenticationPort {

    @Override
    public Long getUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
