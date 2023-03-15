package com.barogo.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

  OK("정상적으로 처리되었습니다."),
  CREATED("정상적으로 생성되었습니다."),
  ;

  private final String message;

}
