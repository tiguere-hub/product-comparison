package com.meli.challenge.ProductComparisons.infrastructure.adapter.persistence.entity;



import java.math.BigDecimal;
import java.util.Map;


public record ProductJSON(String id, String name, String description, String imageUrl, BigDecimal price,
                          Double rating, Map<String, String> attributes
) {}
