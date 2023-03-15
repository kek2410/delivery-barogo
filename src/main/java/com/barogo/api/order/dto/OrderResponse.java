package com.barogo.api.order.dto;

import com.barogo.api.order.code.OrderStatus;
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
public class OrderResponse {

  private Long id;
  private OrderStatus status;
  private String address;
  private String subAddress;
  private String zipCode;
}
