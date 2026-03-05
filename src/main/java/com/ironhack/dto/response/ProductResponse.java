package com.ironhack.dto.response;

import com.ironhack.model.ProductCategory;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price,
        ProductCategory category,
        Integer quantity
) {}