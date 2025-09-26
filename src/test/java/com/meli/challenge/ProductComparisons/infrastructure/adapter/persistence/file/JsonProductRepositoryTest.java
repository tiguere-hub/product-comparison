package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.file;

import com.meli.challenge.ProductComparisons.commons.ProductDataBuilder;
import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity.ProductJSON;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.mapper.ProductJSONMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonProductRepositoryTest {

    @Mock
    private ProductJSONMapper mapper;

    @Mock
    private JsonFileLoader jsonFileLoader;

    @Mock
    private JsonProductRepository self; // Mock the proxy to test getProductsByIds

    @InjectMocks
    private JsonProductRepository jsonProductRepository;


    @Test
    void givenFindById_whenProductExists_thenReturnProductMono() {
        String productId = "ID001";
        var productJson = new ProductJSON(
                "ID001",
                "Laptop Gamer Pro",
                "Laptop de alto rendimiento para juegos.",
                "http://example.com/laptop.jpg",
                new BigDecimal("1599.99"),
                4.8,
                Map.of("CPU", "Intel Core i9", "RAM", "32GB")
        );

        when(jsonFileLoader.getProductMap()).thenReturn(Map.of(productId, productJson));
        when(mapper.toDomain(any())).thenReturn(ProductDataBuilder.PRODUCT_1);

        JsonProductRepository repositoryForFindById = new JsonProductRepository(mapper, null, jsonFileLoader);

        Mono<Product> productMono = repositoryForFindById.findById(productId);

        StepVerifier.withVirtualTime(() -> productMono)
                .thenAwait(Duration.ofSeconds(5))
                .expectNext(ProductDataBuilder.PRODUCT_1)
                .verifyComplete();
    }

    @Test
    void givenFindById_whenProductDoesNotExist_thenReturnEmptyMono() {
        String nonExistentId = "MLA999";
        when(jsonFileLoader.getProductMap()).thenReturn(Collections.emptyMap());

        var repositoryForFindById = new JsonProductRepository(mapper, null, jsonFileLoader);

        Mono<Product> productMono = repositoryForFindById.findById(nonExistentId);

        StepVerifier.withVirtualTime(() -> productMono)
                .thenAwait(Duration.ofSeconds(5))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void givenGetProductsByIds_whenAllIdsAreValid_thenReturnFluxOfProducts() {
        // Arrange
        var id1 = "MLA1";
        var id2 = "MLA2";
        List<String> ids = List.of(id1, id2);

        Product product1 = ProductDataBuilder.PRODUCT_1;
        Product product2 = ProductDataBuilder.PRODUCT_2;

        when(self.findById(id1)).thenReturn(Mono.just(product1));
        when(self.findById(id2)).thenReturn(Mono.just(product2));

        Flux<Product> productsFlux = jsonProductRepository.getProductsByIds(ids);

        StepVerifier.create(productsFlux)
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void givenGetProductsByIds_whenSomeIdsAreInvalid_thenReturnFluxOfFoundProducts() {
        // Arrange
        var validId = "MLA1";
        var invalidId = "MLA999";
        List<String> ids = List.of(validId, invalidId);

        Product product1 = ProductDataBuilder.PRODUCT_1;

        when(self.findById(validId)).thenReturn(Mono.just(product1));
        when(self.findById(invalidId)).thenReturn(Mono.empty());

        Flux<Product> productsFlux = jsonProductRepository.getProductsByIds(ids);

        StepVerifier.create(productsFlux)
                .expectNext(product1)
                .verifyComplete();
    }

    @Test
    void givenGetProductsByIds_whenIdListIsEmpty_thenReturnEmptyFlux() {
        List<String> emptyList = Collections.emptyList();

        Flux<Product> productsFlux = jsonProductRepository.getProductsByIds(emptyList);

        StepVerifier.create(productsFlux)
                .expectNextCount(0)
                .verifyComplete();
    }
}