package com.barogo.common.security;

import com.barogo.api.user.dto.UserDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

  private SecurityUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static String getUserId() {
    return getCustomAuthentication().getUserId();
  }

  public static UserDTO getCustomAuthentication() {
    var auth = getAuthentication();
    if (auth.isEmpty()) {
      return UserDTO.empty();
    }
    return (UserDTO) auth.get().getPrincipal();
  }

  public static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }

}