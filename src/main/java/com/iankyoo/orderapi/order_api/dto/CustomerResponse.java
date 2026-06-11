package com.iankyoo.orderapi.order_api.dto;

import java.time.LocalDateTime;

public record CustomerResponse(Long id, String name, String email, String phone, LocalDateTime createdAt){}
