package com.ironhack.dto.request;

import com.ironhack.model.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, message = "Name must consists of at least 3 characters")
        String name,

        @Positive(message = "Price must be a positive value")
        BigDecimal price,

        @NotNull(message = "Category is required")
        ProductCategory category,

        @Positive(message = "Quantity must be a positive value")
        Integer quantity
) {}
