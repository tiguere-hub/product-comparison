package com.meli.challenge.ProductComparisons.domain.model;

import java.math.BigDecimal;
import java.util.Map;

public record Product(String id,
                      String name,
                      String description,
                      String imgUrl,
                      BigDecimal price,
                      Double rating,
                      Map<String, String> specifications
) { }
