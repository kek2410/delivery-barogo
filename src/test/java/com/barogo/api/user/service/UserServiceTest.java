package com.barogo.api.user.service;

//import static org.assertj.core.api.BDDAssertions.then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.barogo.api.user.UserDataInterface;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.exception.APIException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("인가 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest implements UserDataInterface {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @DisplayName("저장 서비스")
  @Test
  void saveUser() {
    // given
    var request = userSaveRequest();
    given(userRepository.existsByUserId(request.userId())).willReturn(false);
    given(userRepository.save(any())).willReturn(savedUser());
    // when
    var id = userService.save(request);
    // then
    assertTrue(Objects.nonNull(id));
  }


  @DisplayName("중복ID_테스트")
  @Test
  void saveErrorTest() {
    // given
    var request = userSaveRequest();
//    var savedUser = savedUser();
    // when
    given(userRepository.existsByUserId(request.userId())).willReturn(true);
    // then
    var exception = assertThrows(APIException.class, () -> userService.save(request));
    assertEquals("동일한 ID가 존재합니다.", exception.getMessage());
  }

}