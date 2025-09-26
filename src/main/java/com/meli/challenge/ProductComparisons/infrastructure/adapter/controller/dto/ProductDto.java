package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ProductDto(String id,
                      String name,
                      String description,
                      String imgUrl,
                      BigDecimal price,
                      Double rating,
                      Map<String, String> specifications
) { }
