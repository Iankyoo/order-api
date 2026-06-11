package com.iankyoo.orderapi.order_api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMenuItemRequest(
        @NotBlank
        String name,
        String description,
        @Positive
        @NotNull
        BigDecimal price,
        @NotBlank
        String category
) {}
