package com.barogo.api.order.code;

import java.util.Set;

public enum OrderStatus {
  ORDERED, PAID, DELIVERY_READY, DELIVERY_REQUESTED,
  ;

  public static Set<OrderStatus> updatableStatus() {
    return Set.of(ORDERED, PAID, DELIVERY_READY);
  }
}
