package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller;

import com.meli.challenge.ProductComparisons.application.port.in.GetProductsForComparisonUseCase;
import com.meli.challenge.ProductComparisons.commons.ProductDataBuilder;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ComparisonRequestDto;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ProductDto;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.mapper.ProductMapper;
import com.meli.challenge.ProductComparisons.infrastructure.security.ApiKeyAuthFilter;
import com.meli.challenge.ProductComparisons.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(
        controllers = ProductController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, ApiKeyAuthFilter.class}
        )
)
@Import(ProductMapper.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetProductsForComparisonUseCase getProductsForComparisonUseCase;

    @Autowired
    private ProductMapper productMapper;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            return http.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .build();
        }
    }

    @Test
    void getProductDetailsForComparison_whenIdsAreValid_thenReturnProducts() {
        List<String> ids = List.of("MLA1", "MLA2");

        when(getProductsForComparisonUseCase.getProductsByIds(ids)).thenReturn(ProductDataBuilder.getProductsAsFlux());

        webTestClient.post()
                .uri("/v1/products/compare")
                .header("X-API-Key", "MELI_CHALLENGE")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(new ComparisonRequestDto(ids))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON_VALUE)
                .expectBodyList(ProductDto.class)
                .hasSize(2)
                .contains(productMapper.toDto(ProductDataBuilder.PRODUCT_1), productMapper.toDto(ProductDataBuilder.PRODUCT_2));
    }

    @Test
    void getProductDetailsForComparison_whenIdsAreEmpty_thenReturnBadRequest() {

        var emptyRequest = new ComparisonRequestDto(Collections.emptyList());

        webTestClient.post()
                .uri("/v1/products/compare")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(emptyRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getProductDetailsForComparison_whenIdInListIsBlank_thenReturnBadRequest() {

        var blankIdRequest = new ComparisonRequestDto(List.of("MLA1", ""));

        webTestClient.post()
                .uri("/v1/products/compare")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(blankIdRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void givenCompareProductsByIds_whenListHasOneId_thenReturnBadRequest() {

        var oneIdRequest = new ComparisonRequestDto(List.of("MLA1"));

        webTestClient.post()
                .uri("/v1/products/compare")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(oneIdRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void givenCompareProductsByIds_whenListHasMoreThan5_thenReturnBadRequest() {

        var oneIdRequest = new ComparisonRequestDto(List.of("MLA1", "MLA2", "MLA3", "MLA4", "MLA5", "MLA6"));

        webTestClient.post()
                .uri("/v1/products/compare")
                .accept(MediaType.APPLICATION_NDJSON)
                .bodyValue(oneIdRequest)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}