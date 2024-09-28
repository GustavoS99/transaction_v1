package com.emazon.transaction_v1.infrastructure.out.feign.interceptor;

import com.emazon.transaction_v1.infrastructure.out.jwt.util.JwtUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRegistrationRequestInterceptor implements RequestInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final var auth = SecurityContextHolder.getContext().getAuthentication();
        requestTemplate.header("Authorization", "Bearer " + jwtUtil.createToken(auth));
    }
}
