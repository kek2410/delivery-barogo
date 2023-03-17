package com.barogo.api.user.controller;

import com.barogo.api.user.dto.UserLoginRequest;
import com.barogo.api.user.dto.UserSaveRequest;
import com.barogo.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class UserController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Long save(@Valid @RequestBody UserSaveRequest request) {
    return userService.save(request);
  }

  @GetMapping("/login")
  public String login(UserLoginRequest request) {
    return userService.login(request);
  }
}
