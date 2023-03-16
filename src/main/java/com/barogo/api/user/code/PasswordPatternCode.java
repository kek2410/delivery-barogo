package com.barogo.api.user.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PasswordPatternCode {

  SMALL("^[a-z]*"),
  LARGE("^[A-Z]*"),
  NUMBER("^\\d*"),
  EXTRA("^[~!@#$%^&*()-_=+\\|\\[\\]{};:'\",.<>/?]*"),
  ;

  private final String reg;

  public int matched(String text) {
    return text.matches(this.reg) ? 1 : 0;
  }
}
