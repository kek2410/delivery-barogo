package com.barogo.api.user.service;

import java.util.regex.Pattern;

public class UserUtil {

  private UserUtil() {
  }

  public static Pattern PASSWORD_PATTERN = Pattern.compile(
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=_!])(?=.*[a-zA-Z\\\\d@#$%^&+=_!]).{12,}$");
}
