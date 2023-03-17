package com.barogo.api.order.dto;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

public record OrderSearchRequest(
    @NotNull(message = ErrorMessage.MANDATORY)
    LocalDateTime fromDateTime,
    @NotNull(message = ErrorMessage.MANDATORY)
    LocalDateTime toDateTime
) {

  @Builder
  public OrderSearchRequest {
  }
}
