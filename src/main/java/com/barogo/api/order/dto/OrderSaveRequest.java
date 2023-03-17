package com.barogo.api.order.dto;

import com.barogo.api.order.entity.Order;
import com.barogo.api.user.entity.User;
import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OrderSaveRequest(
    @NotBlank(message = ErrorMessage.MANDATORY) String address,
    String subAddress,
    @NotBlank(message = ErrorMessage.MANDATORY) String zipCode
) {

  public Order toEntityWith(User user) {
    return Order.builder()
        .address(address)
        .subAddress(subAddress)
        .zipCode(zipCode)
        .user(user)
        .build();
  }

}
