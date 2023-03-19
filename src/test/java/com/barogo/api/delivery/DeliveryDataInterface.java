package com.barogo.api.delivery;

import com.barogo.api.delivery.code.DeliveryStatus;
import com.barogo.api.delivery.dto.DeliveryResponse;

public interface DeliveryDataInterface {

  default DeliveryResponse deliveryResponse() {
    return DeliveryResponse.builder()
        .id(1L)
        .status(DeliveryStatus.READY)
        .build();
  }
}
