package com.barogo.common.exception;

import com.barogo.common.constant.ErrorCode;

public class APIException extends RuntimeException {

  private final ErrorCode errorCode;

  public APIException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public APIException() {
    super(ErrorCode.UNEXPECT.getMessage());
    this.errorCode = ErrorCode.UNEXPECT;
  }
}
