package com.iankyoo.orderapi.order_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MenuItemResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Boolean available,
        LocalDateTime createdAt
) {
}
