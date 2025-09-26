package com.meli.challenge.ProductComparisons.application.service;

import com.meli.challenge.ProductComparisons.domain.exception.ProductDuplicatedException;
import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.application.port.in.GetProductsForComparisonUseCase;
import com.meli.challenge.ProductComparisons.application.port.out.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This service implement the use cases for the inbound port and Orchestrate business logic.
 * Call the outbound port to get the products
 */
@Service
public class ProductService implements GetProductsForComparisonUseCase {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Product> getProductsByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        Set<String> uniqueIds = new HashSet<>(ids);
        if (uniqueIds.size() != ids.size()) {
            throw new ProductDuplicatedException("Ids can be duplicated");
        }

        return repository.getProductsByIds(ids);
    }
}
