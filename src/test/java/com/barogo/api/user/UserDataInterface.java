package com.barogo.api.user;

import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.entity.User;
import com.barogo.common.constant.UserRole;

public interface UserDataInterface {

  default UserSaveRequest userSaveRequest() {
    var request = new UserSaveRequest();
    request.setUserId("jaein1234");
    request.setName("ohjaein");
    request.setPassword("Password1234!@#$11assd");
    request.setEmail("jaein@jaein.com");
    request.setPhone("010-1234-1234");
    return request;
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

  default UserLoginRequest userSignInRequest() {
    var request = new UserLoginRequest();
    request.setId("jaein1234");
    request.setPassword("password");
    return request;
  }
}
