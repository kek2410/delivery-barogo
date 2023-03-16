package com.barogo.api.user.dto;

import com.barogo.api.user.validator.PasswordPattern;
import com.barogo.common.constant.ErrorMessage;
import com.barogo.common.constant.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {

  @NotBlank(message = ErrorMessage.MANDATORY)
  private String userId;
  @NotBlank(message = ErrorMessage.MANDATORY)
  private String name;
  @NotBlank(message = ErrorMessage.MANDATORY)
  @PasswordPattern
  private String password;
  @NotBlank(message = ErrorMessage.MANDATORY)
  @Email(message = ErrorMessage.INVALID_EMAIL)
  private String email;
  @NotBlank(message = ErrorMessage.MANDATORY)
  @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = ErrorMessage.INVALID_PHONE)
  private String phone;
  @NotNull(message = ErrorMessage.MANDATORY)
  private UserRole role;

}
