package com.barogo.api.user.dto;

import com.barogo.api.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSaveRequest {

  @NotNull
  private String userId;
  @NotNull
  private String name;
  @NotNull
  private String password;
  @NotNull
  @Email
  private String email;
  @NotNull
  @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
  private String phone;



}
