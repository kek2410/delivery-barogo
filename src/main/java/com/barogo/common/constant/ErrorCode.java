package com.barogo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  UNEXPECT("알 수 없는 에러"),
  EXIST_USER("동일한 ID가 존재합니다."),
  NOT_EXIST_USER("ID가 존재하지 않습니다."),
  INVALID_PASSWORD("비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상이며, 12자리 이상의 문자열로 생성해야 합니다"),
  UNMATCHED_PASSWORD("비밀번호가 틀렸습니다.");
  ;

  private final String message;
}
