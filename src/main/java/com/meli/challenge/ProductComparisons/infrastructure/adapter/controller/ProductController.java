package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller;

import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ComparisonRequestDto;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto.ProductDto;
import com.meli.challenge.ProductComparisons.application.port.in.GetProductsForComparisonUseCase;
import com.meli.challenge.ProductComparisons.domain.model.Product;
import com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.mapper.ProductMapper;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * This controller is in charge of managing product Operations
 */
@RestController
@RequestMapping(ProductController.BASE_PATH)
@Validated
public class ProductController {

    static final String BASE_PATH = "/v1/products";

    private final GetProductsForComparisonUseCase getProductsForComparisonUseCase;
    private final ProductMapper mapper;


    public ProductController(GetProductsForComparisonUseCase getProductsForComparisonUseCase, ProductMapper mapper) {
        this.getProductsForComparisonUseCase = getProductsForComparisonUseCase;
        this.mapper = mapper;
    }

    @PostMapping(value = "/compare", produces = MediaType.APPLICATION_NDJSON_VALUE )
    public Flux<ProductDto> compareProductsByIds(
            @RequestBody
            @Valid
            ComparisonRequestDto requestDto) {
        Flux<Product> products = getProductsForComparisonUseCase.getProductsByIds(requestDto.ids());
        return products.map(mapper::toDto);
    }
}
