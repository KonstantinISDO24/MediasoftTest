package com.mediasoft.utils;

import com.mediasoft.controllers.dtos.ProductDto;

import java.math.BigDecimal;

public final class DtoUtils {
    public static ProductDto createProductDto() {
        return ProductDto.builder()
                .name("Test product name")
                .description("Test")
                .price(BigDecimal.TEN)
                .quantity(10)
                .article("Test-1")
                .category("Test")
                .build();

    }

}
