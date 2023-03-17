package com.barogo.api.delivery.dto;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DeliverGetRequest(
    @NotNull(message = ErrorMessage.MANDATORY)
    LocalDateTime fromDate,
    @NotNull(message = ErrorMessage.MANDATORY)
    LocalDateTime toDate) {}
