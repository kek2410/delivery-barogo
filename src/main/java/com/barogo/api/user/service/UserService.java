package com.barogo.api.user.service;

import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.exception.APIException;
import com.barogo.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  public Long save(UserSaveRequest request) {
    if (userRepository.existsByUserId(request.getUserId())) {
      throw new APIException(ErrorCode.EXIST_USER);
    }
    return userRepository.save(convertEntity(request)).getId();
  }

  @Transactional(readOnly = true)
  public String login(UserLoginRequest request) {
    var user = userRepository.findByUserId(request.getUserId())
        .orElseThrow(() -> new APIException(ErrorCode.NOT_EXIST_USER));
    if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new APIException(ErrorCode.WRONG_PASSWORD);
    }
    return jwtTokenProvider.create(user.getUserId(), user.getRole());
  }

  @Transactional(readOnly = true)
  public User getUserByUserId(String userId) {
    return userRepository.findByUserId(userId)
        .orElseThrow(() -> new APIException(ErrorCode.NOT_EXIST_USER));
  }

  private User convertEntity(UserSaveRequest request) {
    return User.builder()
        .userId(request.getUserId())
        .name(request.getName())
        .password(bCryptPasswordEncoder.encode(request.getPassword()))
        .email(request.getEmail())
        .phone(request.getPhone())
        .role(request.getRole())
        .build();
  }
}
