package com.barogo.common.exception;

import com.barogo.common.constant.ErrorCode;

public class APIException extends RuntimeException {

  private final ErrorCode errorCode;

  public APIException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public APIException() {
    this.errorCode = ErrorCode.UNEXPECT;
  }
}
