package com.barogo.common.exception;

import com.barogo.common.constant.ErrorCode;

public class NotExistDataException extends APIException {

  public NotExistDataException() {
    super(ErrorCode.NOT_EXIST_DATA);
  }
}
