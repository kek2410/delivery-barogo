package com.barogo.api.user.delivery.dto;

import com.barogo.api.user.delivery.entity.Delivery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliverySaveRequest {

  private String status;

  public Delivery toEntity() {
    return Delivery.builder()
        .status(status)
        .build();
  }
}
