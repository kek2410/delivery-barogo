package com.barogo.api.user.dto;

import com.barogo.api.user.validator.PasswordPattern;
import com.barogo.common.constant.ErrorMessage;
import com.barogo.common.constant.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserSaveRequest(
    @NotBlank(message = ErrorMessage.MANDATORY)
    String userId,
    @NotBlank(message = ErrorMessage.MANDATORY)
    String name,
    @NotBlank(message = ErrorMessage.MANDATORY)
    @PasswordPattern
    String password,
    @NotBlank(message = ErrorMessage.MANDATORY)
    @Email(message = ErrorMessage.INVALID_EMAIL)
    String email,
    @NotBlank(message = ErrorMessage.MANDATORY)
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = ErrorMessage.INVALID_PHONE)
    String phone,
    @NotNull(message = ErrorMessage.MANDATORY)
    UserRole role
) {}
