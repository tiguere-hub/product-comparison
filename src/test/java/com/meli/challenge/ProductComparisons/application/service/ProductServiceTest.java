package com.meli.challenge.ProductComparisons.application.service;

import com.meli.challenge.ProductComparisons.application.port.out.ProductRepository;
import com.meli.challenge.ProductComparisons.commons.ProductDataBuilder;
import com.meli.challenge.ProductComparisons.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void givenGetProductsByIds_whenGivenValidIds_thenReturnProducts() {
        List<String> ids = List.of("MLA1", "MLA2");
        when(productRepository.getProductsByIds(ids)).thenReturn(ProductDataBuilder.getProductsAsFlux());

        Flux<Product> resultFlux = productService.getProductsByIds(ids);

        StepVerifier.create(resultFlux)
                .expectNext(ProductDataBuilder.PRODUCT_1)
                .expectNext(ProductDataBuilder.PRODUCT_2)
                .verifyComplete();

        verify(productRepository, times(1)).getProductsByIds(ids);
    }

    @Test
    void givenGetProductsByIds_whenGivenEmptyList_thenReturnEmptyFlux() {
        List<String> emptyList = Collections.emptyList();

        Flux<Product> resultFlux = productService.getProductsByIds(emptyList);

        StepVerifier.create(resultFlux).verifyComplete();

        verify(productRepository, never()).getProductsByIds(any());
    }

    @Test
    void givenGetProductsByIds_whenGivenNullList_thenReturnEmptyFlux() {
        Flux<Product> resultFlux = productService.getProductsByIds(null);

        StepVerifier.create(resultFlux).verifyComplete();

        verify(productRepository, never()).getProductsByIds(any());
    }

    @Test
    void givenGetProductsByIds_whenThereAreDuplicatedElements_thenThrowException() {
        List<String> duplicatedElements = List.of("1", "1");

        assertThrows(IllegalArgumentException.class, () -> productService.getProductsByIds(duplicatedElements));

        verify(productRepository, never()).getProductsByIds(any());
    }
}
