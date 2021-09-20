package com.example.spring_security_jwt_auth.controller;

import com.example.spring_security_jwt_auth.model.User;
import com.example.spring_security_jwt_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {

    List<User> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }


}
