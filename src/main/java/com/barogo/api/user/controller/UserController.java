package com.barogo.api.user.controller;

import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "사용자 관리 API")
public class UserController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "회원가입")
  public Long save(@Valid @RequestBody UserSaveRequest request) {
    return userService.save(request);
  }

  @GetMapping("/login")
  @Operation(description = "로그인")
  public String login(UserLoginRequest request) {
    return userService.login(request);
  }
}
