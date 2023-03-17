package com.barogo.api.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.barogo.AbstractControllerTest;
import com.barogo.api.user.UserDataInterface;
import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.service.UserService;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.constant.ErrorMessage;
import com.barogo.common.exception.APIException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserController.class)
@DisplayName("사용자 컨트롤러")
class UserControllerTest extends AbstractControllerTest implements UserDataInterface {

  private static final String BASE_URL = "/users";

  @MockBean
  private UserService userService;

  @DisplayName("사용자 등록")
  @Test
  void saveUser() throws Exception {
    // given
    var request = userSaveRequest();
    given(userService.save(any(UserSaveRequest.class))).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(request)));
    // then
    perform.andExpect(STATUS_IS_CREATED)
        .andExpect(result("1"));
  }

  @DisplayName("사용자 등록 실패1")
  @Test
  void saveFailed() throws Exception {
    // given
    var request = UserSaveRequest.builder().build();
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .content(toJson(request)));
    // then
    perform.andExpect(STATUS_IS_BAD_REQUEST);
  }

  @DisplayName("로그인 실패 ID 없음")
  @Test
  void notExistUser() throws Exception {
    // given
    var request = userLoginRequest();
    given(userService.login(any(UserLoginRequest.class)))
        .willThrow(new APIException(ErrorCode.NOT_EXIST_USER));
    // when
    var perform = mvc.perform(get(BASE_URL + "/login")
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform
        .andExpect(STATUS_IS_UNPROCESSABLE_ENTITY)
        .andExpect(result(ErrorMessage.NOT_EXIST_USER));
  }

  @DisplayName("로그인 실패 비밀번호 틀림.")
  @Test
  void wrongPassword() throws Exception {
    // given
    var request = userLoginRequest();
    given(userService.login(any(UserLoginRequest.class)))
        .willThrow(new APIException(ErrorCode.WRONG_PASSWORD));
    // when
    var perform = mvc.perform(get(BASE_URL + "/login")
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform
        .andExpect(STATUS_IS_UNPROCESSABLE_ENTITY)
        .andExpect(result(ErrorMessage.WRONG_PASSWORD));
  }

  @DisplayName("로그인 성공")
  @Test
  void findUserById() throws Exception {
    // given
    var request = userLoginRequest();
    given(userService.login(any())).willReturn("testToken");
    // when
    var perform = mvc.perform(get(BASE_URL + "/login")
        .contentType(CONTENT_TYPE)
        .queryParams(toMap(request)));
    // then
    perform
        .andExpect(STATUS_IS_OK)
        .andExpect(result("testToken"));
  }

}