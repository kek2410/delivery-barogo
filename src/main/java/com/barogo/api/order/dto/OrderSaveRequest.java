package com.barogo.api.order.dto;

import com.barogo.api.order.entity.Order;
import com.barogo.api.user.entity.User;
import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRequest {

  @NotBlank(message = ErrorMessage.MANDATORY)
  private String address;

  private String subAddress;

  @NotBlank(message = ErrorMessage.MANDATORY)
  private String zipCode;

  public Order toEntityWith(User user) {
    return Order.builder()
        .address(address)
        .subAddress(subAddress)
        .zipCode(zipCode)
        .user(user)
        .build();
  }

}
