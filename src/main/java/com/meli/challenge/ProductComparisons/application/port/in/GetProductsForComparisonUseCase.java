package com.meli.challenge.ProductComparisons.application.port.in;

import com.meli.challenge.ProductComparisons.domain.model.Product;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Inbound port to compare products
 */
public interface GetProductsForComparisonUseCase {
    Flux<Product> getProductsByIds(List<String> ids);
}
