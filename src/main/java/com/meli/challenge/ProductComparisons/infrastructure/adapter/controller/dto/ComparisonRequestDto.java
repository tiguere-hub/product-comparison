package com.meli.challenge.ProductComparisons.infrastructure.adapter.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ComparisonRequestDto(@Size(max = 5, min = 2) @NotEmpty(message = "Product ids list cannot be empty")
                                   List<@NotBlank(message = "Don't support empty or null ids") String> ids) { }
