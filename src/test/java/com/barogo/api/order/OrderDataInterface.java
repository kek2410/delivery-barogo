package com.barogo.api.order;

import com.barogo.api.order.code.OrderStatus;
import com.barogo.api.order.dto.OrderResponse;
import com.barogo.api.order.dto.OrderSaveRequest;

public interface OrderDataInterface {

  default OrderSaveRequest saveOrderRequest() {
    return OrderSaveRequest.builder()
        .address("서울시 어딘가 서울대로1길")
        .subAddress("1321호")
        .zipCode("12345")
        .build();
  }

  default OrderResponse getOrderResponse() {
    return OrderResponse.builder()
        .id(1L)
        .status(OrderStatus.ORDERED)
        .address("address")
        .subAddress("subAddress")
        .zipCode("zipCode")
        .build();
  }
}
