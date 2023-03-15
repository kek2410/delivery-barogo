package com.barogo.api.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.barogo.RepositoryTest;
import com.barogo.api.user.UserDataInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
@DisplayName("유저 리포지토리 테스트")
class UserRepositoryTest implements UserDataInterface {

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    var savedUser = savedUser();
    userRepository.save(savedUser);
  }

  @DisplayName("사용자 조회")
  @Test
  void get() {
    // given
    var userId = savedUser().getUserId();
    // when
    var user = userRepository.findByUserId(userId).orElseThrow();
    // the
    System.out.println(user);
    assertEquals(user.getUserId(), userId);
  }
  
  @DisplayName("ID 존재 유무 확인")
  @Test
  void duplicatedId() {
    // given
    var userId = savedUser().getUserId();
    // when
    var exists = userRepository.existsByUserId(userId);
    // the
    assertTrue(exists);
  }

}