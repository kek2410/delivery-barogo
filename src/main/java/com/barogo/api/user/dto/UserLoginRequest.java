package com.barogo.api.user.dto;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserLoginRequest(
    @NotNull(message = ErrorMessage.MANDATORY)
    String userId,
    @NotNull
    String password) {
}
