package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.mapper;

import com.meli.challenge.ProductComparisons.commons.ProductDataBuilder;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    void givenProductModel_whenIsNotNull_thenShouldMapToProductDto() {
        var productModel = ProductDataBuilder.PRODUCT_1;

        ProductDto resultDto = mapper.toDto(productModel);

        assertNotNull(resultDto);
        assertEquals(productModel.id(), resultDto.id());
        assertEquals(productModel.name(), resultDto.name());
        assertEquals(productModel.description(), resultDto.description());
        assertEquals(productModel.imgUrl(), resultDto.imgUrl());
        assertEquals(productModel.price(), resultDto.price());
        assertEquals(productModel.rating(), resultDto.rating());
        assertEquals(productModel.specifications(), resultDto.specifications());
    }

    @Test
    void givenProductDto_whenIsNull_shouldReturnNull() {
        ProductDto resultDto = mapper.toDto(null);
        assertNull(resultDto);
    }
}
