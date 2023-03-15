package com.barogo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  UNEXPECT(ErrorMessage.UNKNOWN),
  EXIST_USER(ErrorMessage.EXIST_USER),
  NOT_EXIST_USER(ErrorMessage.NOT_EXIST_USER),
  INVALID_PASSWORD(ErrorMessage.INVALID_PASSWORD),
  WRONG_PASSWORD(ErrorMessage.WRONG_PASSWORD),
  NOT_EXIST_DATA(ErrorMessage.NOT_EXIST_DATA),
  INVALID_STATUS_FOR_UPDATE_ADDRESS(ErrorMessage.INVALID_STATUS_FOR_UPDATE_ADDRESS),
  INVALID_STATUS_FOR_PAID(ErrorMessage.INVALID_STATUS_FOR_PAID),
  INVALID_STATUS_FOR_DELIVERY_READY(ErrorMessage.INVALID_STATUS_FOR_DELIVERY_READY),
  INVALID_STATUS_FOR_DELIVERY_REQUEST(ErrorMessage.INVALID_STATUS_FOR_DELIVERY_REQUEST),
  ;

  private final int status = 422;
  private final String message;
}
