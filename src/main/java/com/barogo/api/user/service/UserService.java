package com.barogo.api.user.service;

import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.dto.UserSignInRequest;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.exception.APIException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  public Long save(UserSaveRequest request) {
    if (userRepository.existsByUserId(request.getUserId())) {
      throw new APIException(ErrorCode.EXIST_USER);
    }
    return userRepository.save(convertEntity(request)).getId();
  }

  @Transactional(readOnly = true)
  public String signIn(UserSignInRequest request) {
    var password = userRepository.findByUserId(request.getId())
        .orElseThrow(() -> new APIException(ErrorCode.NOT_EXIST_USER))
        .getPassword();
    if (bCryptPasswordEncoder.matches(request.getPassword(), password)) {
      throw new APIException(ErrorCode.UNMATCHED_PASSWORD);
    }
    return "";
  }

  private User convertEntity(UserSaveRequest request) {
    return User.builder()
        .userId(request.getUserId())
        .name(request.getName())
        .password(bCryptPasswordEncoder.encode(request.getPassword()))
        .email(request.getEmail())
        .phone(request.getPhone())
        .build();
  }
}
