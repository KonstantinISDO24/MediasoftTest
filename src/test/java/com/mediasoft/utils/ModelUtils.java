package com.mediasoft.utils;

import com.mediasoft.models.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ModelUtils {

    public static Product createProduct(UUID id) {
        return Product.builder()
                .id(id)
                .name("Test product name")
                .description("Test")
                .price(BigDecimal.TEN)
                .creationDate(LocalDate.now())
                .quantityLastUpdateDate(LocalDateTime.now())
                .quantity(10)
                .article("Test-1")
                .category("Test")
                .build();
    }
}
