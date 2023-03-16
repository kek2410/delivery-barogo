package com.barogo.api.order.dto;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderSearchRequest {

  @NotNull(message = ErrorMessage.MANDATORY)
  private LocalDateTime fromDateTime;

  @NotNull(message = ErrorMessage.MANDATORY)
  private LocalDateTime toDateTime;
}
