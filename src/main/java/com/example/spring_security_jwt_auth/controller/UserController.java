package com.example.spring_security_jwt_auth.controller;

import com.example.spring_security_jwt_auth.dto.UserFormDto;
import com.example.spring_security_jwt_auth.model.Role;
import com.example.spring_security_jwt_auth.model.User;
import com.example.spring_security_jwt_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  @Value("${url.base}")
  private String baseUrl;

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {

    List<User> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @PostMapping("/users/user")
  public ResponseEntity<User> saveUser(@RequestBody User user) {

    User storedUser = userService.saveUser(user);
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/user")
                .toUriString());

    return ResponseEntity.created(uri).body(storedUser);
  }

  @PostMapping("/roles/role")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {

    Role storedRole = userService.saveRole(role);
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/roles/role")
                .toUriString());
    return ResponseEntity.created(uri).body(storedRole);
  }

  @PutMapping("/roles/role")
  public ResponseEntity<Void> addRoleToUser(@RequestBody UserFormDto dto) {

    userService.addRoleToUser(dto.getUsername(), dto.getRole());

    return ResponseEntity.noContent().build();
  }


}
