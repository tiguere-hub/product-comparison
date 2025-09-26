package com.meli.challenge.ProductComparisons.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyAuthFilter implements WebFilter {

    @Value("${api.security.key}")
    private String requiredApiKey;

    private static final String API_KEY_HEADER = "X-API-Key";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String providedApiKey = exchange.getRequest().getHeaders().getFirst(API_KEY_HEADER);

        if (providedApiKey == null || !providedApiKey.equals(requiredApiKey)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}