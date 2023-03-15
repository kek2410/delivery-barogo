package com.barogo.common.constant;

import java.util.Base64;

public class Token {

  private Token() {
  }

  public static final String AUDIENCE = "https://barogo.com";
  public static final String ISSUER = "barogo";
  public static final String SECRET_KEY = "YmFyb2dv";
  public static final long TOKEN_EXPIRED_AT = 60 * 60 * 1000L;
}
