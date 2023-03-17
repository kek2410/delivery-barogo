package com.barogo.api.order.dto;

import com.barogo.api.order.code.OrderStatus;
import lombok.Builder;

@Builder
public record OrderResponse(
    Long id,
    OrderStatus status,
    String address,
    String subAddress,
    String zipCode
) {}
