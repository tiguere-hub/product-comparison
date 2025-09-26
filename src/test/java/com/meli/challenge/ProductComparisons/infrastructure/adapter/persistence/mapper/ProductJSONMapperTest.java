package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.mapper;

import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity.ProductJSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductJSONMapperTest {

    private ProductJSONMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductJSONMapper();
    }

    @Test
    void givenProductJson_whenIsNotNull_thenShouldMapToProduct() {
        var productJSON = new ProductJSON(
                "MLA1",
                "Product 1",
                "Desc 1",
                "url1",
                new BigDecimal("100.00"),
                4.5,
                null
        );

        Product domainProduct = mapper.toDomain(productJSON);

        assertNotNull(domainProduct);
        assertEquals(productJSON.id(), domainProduct.id());
        assertEquals(productJSON.name(), domainProduct.name());
        assertEquals(productJSON.description(), domainProduct.description());
        assertEquals(productJSON.imageUrl(), domainProduct.imgUrl());
        assertEquals(productJSON.price(), domainProduct.price());
        assertEquals(productJSON.rating(), domainProduct.rating());
        assertEquals(productJSON.attributes(), domainProduct.specifications());
    }

    @Test
    void givenProductJson_whenIsNull_shouldReturnNull() {
        Product resultDto = mapper.toDomain(null);
        assertNull(resultDto);
    }
}
