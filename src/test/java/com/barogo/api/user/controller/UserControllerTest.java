package com.barogo.api.user.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.barogo.AbstractControllerTest;
import com.barogo.api.user.UserDataInterface;
import com.barogo.api.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser
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
    given(userService.save(request)).willReturn(1L);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .queryParams(convertParam(request)));
    // then
    perform.andExpect(IS_CREATED)
        .andExpect(result("0"));
  }

  @DisplayName("사용자 등록 실패1")
  @Test
  void saveFailed() throws Exception {
    // given
    var request = userSaveRequest();
    request.setPassword(null);
    // when
    var perform = mvc.perform(post(BASE_URL)
        .contentType(CONTENT_TYPE)
        .queryParams(convertParam(request)));
    // then
    perform.andExpect(IS_BAD_REQUEST);
  }

  @DisplayName("인증실패")
  @Test
  void unauthorized() {
    // given

    // when

    // the
  }

  @DisplayName("사용자 토큰 발행")
  @Test
  void findUserById() throws Exception {
    // given

    // when

    // then
    mvc.perform(get(BASE_URL + "/sign-in"))
        .andExpect(IS_OK)
        .andReturn();

  }

}