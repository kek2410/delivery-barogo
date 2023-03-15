package com.barogo.api.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginRequest {

  @NotNull
  private String id;

  @NotNull
  private String password;
}
