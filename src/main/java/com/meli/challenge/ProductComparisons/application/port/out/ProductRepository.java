package com.meli.challenge.ProductComparisons.application.port.out;

import com.meli.challenge.ProductComparisons.domain.model.Product;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Outbound port to connect to the json file provider
 */
public interface ProductRepository {
    Flux<Product> getProductsByIds(List<String> ids);
}
