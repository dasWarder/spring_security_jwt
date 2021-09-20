package com.example.spring_security_jwt_auth.service;

import com.example.spring_security_jwt_auth.model.Role;
import com.example.spring_security_jwt_auth.model.User;

import java.util.List;

public interface UserService {

  User saveUser(User user);

  Role saveRole(Role role);

  void addRoleToUser(String username, String role);

  User getUser(String username);

  List<User> getUsers();
}
