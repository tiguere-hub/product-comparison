package com.meli.challenge.ProductComparisons.commons;


import com.meli.challenge.ProductComparisons.domain.model.Product;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

public class ProductDataBuilder {

    private ProductDataBuilder() {}

    public static final Product PRODUCT_1 = new Product(
            "MLA1",
            "Product 1",
            "Desc 1",
            "url1",
            new BigDecimal("100.00"),
            4.5,
            null
    );

    public static final Product PRODUCT_2 = new Product(
            "MLA2",
            "Product 2",
            "Desc 2",
            "url2",
            new BigDecimal("200.00"),
            5.0,
            null
    );

    public static Flux<Product> getProductsAsFlux() {
        return Flux.just(PRODUCT_1, PRODUCT_2);
    }
}
