package com.barogo.api.user;

import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.entity.User;
import com.barogo.common.constant.UserRole;

public interface UserDataInterface {

  default UserSaveRequest userSaveRequest() {
    return UserSaveRequest.builder()
        .userId("jaein1234")
        .name("ohjaein")
        .password("Password1234!@#$")
        .email("jaein@jaein.com")
        .phone("010-1234-1234")
        .role(UserRole.ADMIN)
        .build();
  }

  default User savedUser() {
    return User.builder()
        .id(1L)
        .userId("jaein1234")
        .name("ohjaein")
        .password("test")
        .phone("010-1234-1234")
        .email("jaein@jaein.com")
        .role(UserRole.CUSTOMER)
        .build();
  }

  default UserLoginRequest userLoginRequest() {
    return UserLoginRequest.builder()
        .userId("jaein1234")
        .password("password")
        .build();
  }
}
