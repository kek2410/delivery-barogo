package com.barogo.api.delivery.dto;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliverGetRequest {

  @NotNull(message = ErrorMessage.MANDATORY)
  private LocalDateTime fromDate;

  @NotNull(message = ErrorMessage.MANDATORY)
  private LocalDateTime toDate;
}
