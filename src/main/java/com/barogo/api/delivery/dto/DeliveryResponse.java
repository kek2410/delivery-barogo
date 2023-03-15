package com.barogo.api.delivery.dto;

import com.barogo.api.delivery.code.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

  private Long id;

  private DeliveryStatus status;

}
