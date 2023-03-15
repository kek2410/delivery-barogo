package com.barogo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

  CREATED("정상적으로 생성되었습니다."),
  ;

  private final String message;

}
