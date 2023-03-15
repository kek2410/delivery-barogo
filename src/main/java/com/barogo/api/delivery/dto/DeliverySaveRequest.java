package com.barogo.api.delivery.dto;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.entity.Delivery;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliverySaveRequest {

  private DeliveryStatus status;

  private LocalDateTime fromDate;

  private LocalDateTime toDate;

  public Delivery toEntity() {
    return Delivery.builder()
        .status(status)
        .build();
  }
}
