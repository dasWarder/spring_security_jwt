package com.example.spring_security_jwt_auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.spring_security_jwt_auth.dto.RefreshedToken;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

  @Value("${url.base}")
  private String baseUrl;

  private final UserService userService;

  @GetMapping("/api/users")
  public ResponseEntity<List<User>> getUsers() {

    List<User> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @PostMapping("/api/users/user")
  public ResponseEntity<User> saveUser(@RequestBody User user) {

    User storedUser = userService.saveUser(user);
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/user")
                .toUriString());

    return ResponseEntity.created(uri).body(storedUser);
  }

  @PostMapping("/api/roles/role")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {

    Role storedRole = userService.saveRole(role);
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/roles/role")
                .toUriString());
    return ResponseEntity.created(uri).body(storedRole);
  }

  @PutMapping("/api/roles/role")
  public ResponseEntity<Void> addRoleToUser(@RequestBody UserFormDto dto) {

    userService.addRoleToUser(dto.getUsername(), dto.getRole());

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/token/refresh")
  public ResponseEntity<RefreshedToken> refreshToken(
      @RequestParam("refreshToken") String refreshToken) {

    if (refreshToken != null) {

      try {

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        String username = decodedJWT.getSubject();
        User user = userService.getUser(username);

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(baseUrl + "/token/refresh")
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

        RefreshedToken refreshedToken = new RefreshedToken(refreshToken, accessToken);

        return ResponseEntity.ok(refreshedToken);

      } catch (Exception exc) {

        throw new RuntimeException("Wrong refresh token");
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }
}
