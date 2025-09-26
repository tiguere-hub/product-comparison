package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.file;


import com.meli.challenge.ProductComparisons.application.port.out.ProductRepository;
import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.mapper.ProductJSONMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * This repo is in charge of getting the product detail using the ids
 */
@Repository
public class JsonProductRepository implements ProductRepository {

    private final ProductJSONMapper mapper;
    private final JsonFileLoader jsonFileLoader;
    private final JsonProductRepository self; //We Need to do this because we are caching products and this class use a proxy

    public JsonProductRepository(ProductJSONMapper mapper, @Lazy JsonProductRepository self, JsonFileLoader jsonFileLoader) {
        this.mapper = mapper;
        this.self = self;
        this.jsonFileLoader = jsonFileLoader;
    }

    /**
     * This method find the product in the jsonFIleLoader if not cached
     */
    @Cacheable(value = "products", key = "#id")
    public Mono<Product> findById(String id) {
        return Mono.justOrEmpty(mapper.toDomain(jsonFileLoader.getProductMap().get(id)))
                .delayElement(Duration.ofSeconds(5));
    }


    @Override
    public Flux<Product> getProductsByIds(List<String> ids) {
        return Flux.fromIterable(ids)
                .flatMap(self::findById);
    }
}
