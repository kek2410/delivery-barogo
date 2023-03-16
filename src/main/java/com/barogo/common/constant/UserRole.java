package com.barogo.common.constant;

import java.util.Arrays;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  CUSTOMER, DELIVER, ADMIN;

  @Override
  public String getAuthority() {
    return this.name();
  }

  public static UserRole parse(String role) {
    return Arrays.stream(values()).filter(value -> value.name().equals(role))
        .findAny()
        .orElse(null);
  }
}
