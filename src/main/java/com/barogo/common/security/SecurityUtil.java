package com.barogo.common.security;

import com.barogo.api.user.dto.UserDTO;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.exception.APIException;
import java.util.Objects;
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
    return (UserDTO) getAuthentication().getPrincipal();
  }

  public static Authentication getAuthentication() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(auth)) {
      throw new APIException(ErrorCode.NOT_EXIST_USER);
    }
    return auth;
  }

}