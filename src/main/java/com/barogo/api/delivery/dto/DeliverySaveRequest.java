package com.barogo.api.delivery.dto;

import com.barogo.api.delivery.code.DeliveryStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DeliverySaveRequest(
    DeliveryStatus status,
    LocalDateTime fromDate,
    LocalDateTime toDate
) {}
