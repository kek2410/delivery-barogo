package com.barogo.api.user.service;

//import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.exception.APIException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("인가 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
    var request = new UserSaveRequest();
    request.setUserId("jaein1234");
    request.setName("ohjaein");
    request.setPassword("password");
    request.setEmail("jaein@jaein.com");
    request.setPhone("010-1234-1234");
    var savedUser = User.builder()
        .id(1L)
        .userId(request.getUserId())
        .password("test")
        .phone(request.getPhone())
        .email(request.getEmail())
        .build();
    given(userRepository.existsByUserId(request.getUserId())).willReturn(false);
    given(userRepository.save(any())).willReturn(savedUser);
    // when
    var id = userService.save(request);
    // then
//    assertTrue(Objects.nonNull(id));
//    then(id).isEqualTo(1L);
    then(userRepository).should().existsByUserId(any(String.class));
    then(userRepository).should().save(any(User.class));
//    then(id).should();
  }

  @DisplayName("중복ID_테스트")
  @Test
  void saveErrorTest() {
    // given
    var request = new UserSaveRequest();
    request.setUserId("jaein1234");
    request.setName("ohjaein");
    request.setPassword("password");
    request.setEmail("jaein@jaein.com");
    request.setPhone("010-1234-1234");
    var savedUser = User.builder()
        .id(1L)
        .userId(request.getUserId())
        .password("test")
        .phone(request.getPhone())
        .email(request.getEmail())
        .build();
    // when
    given(userRepository.existsByUserId(request.getUserId())).willReturn(true);
    // then
    var exception = assertThrows(APIException.class, () -> userService.save(request));
    assertEquals("동일한 ID가 존재합니다.", exception.getMessage());
  }

}