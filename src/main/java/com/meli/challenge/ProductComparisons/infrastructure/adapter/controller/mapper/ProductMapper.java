package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.mapper;

import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product model) {
        if (model == null) {
            return null;
        }
        return new ProductDto(
                model.id(),
                model.name(),
                model.description(),
                model.imgUrl(),
                model.price(),
                model.rating(),
                model.specifications()
        );
    }
}
