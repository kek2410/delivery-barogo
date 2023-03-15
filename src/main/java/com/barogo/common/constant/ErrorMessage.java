package com.barogo.common.constant;

public class ErrorMessage {

  private ErrorMessage() {

  }

  public static final String UNKNOWN = "알 수 없는 에러.";
  public static final String MANDATORY = "필수 입력값입니다.";
  public static final String EXIST_USER = "동일한 ID가 존재합니다.";
  public static final String NOT_EXIST_USER = "ID가 존재하지 않습니다.";
  public static final String INVALID_EMAIL = "이메일의 형태가 아닙니다.";
  public static final String INVALID_PHONE = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx";
  public static final String INVALID_PASSWORD = "비밀번호는 영문 대문자, 소문자, 숫자, 특수문자중 3종류 이상이 포함되어야 하며 12자리 이상이어야 합니다.";
  public static final String WRONG_PASSWORD = "비밀번호가 틀렸습니다.";
  public static final String NOT_EXIST_DATA = "데이터가 존재하지 않습니다.";
  public static final String INVALID_STATUS_FOR_UPDATE_ADDRESS = "배송주소를 변경할 수 없습니다.";
  public static final String INVALID_STATUS_FOR_PAID = "결제완료로 변경할 수 없는 상태입니다.";
  public static final String INVALID_STATUS_FOR_DELIVERY_READY = "배송준비로 변경할 수 없는 상태입니다.";
  public static final String INVALID_STATUS_FOR_DELIVERY_REQUEST = "배송요청으로 준비할 수 없는 상태입니다.";

}
