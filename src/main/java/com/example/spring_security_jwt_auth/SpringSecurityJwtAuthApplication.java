package com.example.spring_security_jwt_auth;

import com.example.spring_security_jwt_auth.model.Role;
import com.example.spring_security_jwt_auth.model.User;
import com.example.spring_security_jwt_auth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityJwtAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityJwtAuthApplication.class, args);
  }

  @Bean
  CommandLineRunner run(UserService userService) {
    return args -> {
      userService.saveRole(new Role(null, "ROLE_USER"));
      userService.saveRole(new Role(null, "ROLE_MANAGER"));
      userService.saveRole(new Role(null, "ROLE_ADMIN"));
      userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

      userService.saveUser(
          new User(null, "Andrey Petrov", "daswarder", "12345", new ArrayList<>()));
      userService.saveUser(new User(null, "Jack Smith", "jack", "12345", new ArrayList<>()));
      userService.saveUser(new User(null, "David Johns", "david", "12345", new ArrayList<>()));
      userService.saveUser(new User(null, "Will Travolta", "will", "12345", new ArrayList<>()));

      userService.addRoleToUser("daswarder", "ROLE_SUPER_ADMIN");
      userService.addRoleToUser("daswarder", "ROLE_ADMIN");
      userService.addRoleToUser("jack", "ROLE_USER");
      userService.addRoleToUser("jack", "ROLE_ADMIN");
      userService.addRoleToUser("david", "ROLE_MANAGER");
      userService.addRoleToUser("will", "ROLE_USER");
    };
  }
}
