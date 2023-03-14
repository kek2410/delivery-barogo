package com.barogo.common.constant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public enum ResponseMessage {

  CREATED(),
  ;

  private final String message;

}
