package com.barogo.api.user.code;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PasswordPatternCode {

  SMALL(Pattern.compile(".*[a-z].*")),
  LARGE(Pattern.compile(".*[A-Z].*")),
  NUMBER(Pattern.compile(".*\\d.*")),
  EXTRA(Pattern.compile(".*[~!@#$%^&*()-_=+|\\[\\]{};:'\",.<>/?].*")),
  ;

  private final Pattern pattern;

  public int matched(String text) {
    return this.pattern.matcher(text).matches() ? 1 : 0;
  }
}
