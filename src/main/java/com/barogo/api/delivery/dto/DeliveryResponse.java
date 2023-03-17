package com.barogo.api.delivery.dto;

import com.barogo.api.delivery.code.DeliveryStatus;
import lombok.Builder;

@Builder
public record DeliveryResponse(
    Long id,
    DeliveryStatus status
) {}
