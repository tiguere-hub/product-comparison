package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.mapper;

import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity.ProductJSON;
import org.springframework.stereotype.Component;

@Component
public class ProductJSONMapper {

    public Product toDomain(ProductJSON entity) {
        if (entity == null) {
            return null;
        }
        return new Product(
                entity.id(),
                entity.name(),
                entity.description(),
                entity.imageUrl(),
                entity.price(),
                entity.rating(),
                entity.attributes()
        );
    }
}
