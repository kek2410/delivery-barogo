package com.barogo.api.user.service;

import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.entity.User;
import com.barogo.api.user.repository.UserRepository;
import com.barogo.common.constant.ErrorCode;
import com.barogo.common.exception.APIException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import java.util.Calendar;
import java.util.Date;
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
  public String login(UserLoginRequest request) {
    var user = userRepository.findByUserId(request.getId())
        .orElseThrow(() -> new APIException(ErrorCode.NOT_EXIST_USER));
    if (bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new APIException(ErrorCode.WRONG_PASSWORD);
    }
    return getToken(user);
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
        .build();
  }

  private String getToken(User user) {
    return new DefaultJwtBuilder()
        .setHeaderParam("kid", "USER_KEY")
        .setIssuer("OH_JAE_IN")
        .setIssuedAt(new Date(Calendar.getInstance().getTimeInMillis()))
        .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + (60 * 60 * 1000)))
        .setAudience(user.getUserId())
        .claim("userId", user.getUserId())
        .signWith(SignatureAlgorithm.RS256, "OH_JAE_IN")
        .compact();
  }
}
