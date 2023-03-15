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
  ;

  private final int status = 422;
  private final String message;
}
