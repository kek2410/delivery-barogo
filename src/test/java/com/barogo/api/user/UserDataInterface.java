package com.barogo.api.user;

import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.dto.UserSignInRequest;
import com.barogo.api.user.entity.User;

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
        .password("test")
        .phone("010-1234-1234")
        .email("jaein@jaein.com")
        .build();
  }

  default UserSignInRequest userSignInRequest() {
    var request = new UserSignInRequest();
    request.setId("jaein1234");
    request.setPassword("password");
    return request;
  }
}
