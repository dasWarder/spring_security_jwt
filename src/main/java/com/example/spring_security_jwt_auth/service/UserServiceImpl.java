package com.example.spring_security_jwt_auth.service;

import com.example.spring_security_jwt_auth.model.Role;
import com.example.spring_security_jwt_auth.model.User;
import com.example.spring_security_jwt_auth.repository.RoleRepository;
import com.example.spring_security_jwt_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  @Override
  public User saveUser(User user) {

    log.info("Store a user");

    return userRepository.save(user);
  }

  @Override
  public Role saveRole(Role role) {

    log.info("Store a role");

    return roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String role) {

    log.info("Add a role = {} to a user with a username = {}", role, username);

    Role roleByName = roleRepository.findRoleByName(role);
    userRepository.findUserByUsername(username).getRoles().add(roleByName);
  }

  @Override
  public User getUser(String username) {

    log.info("Receive a user with a username = {}", username);

    return userRepository.findUserByUsername(username);
  }

  @Override
  public List<User> getUsers() {

    log.info("Receive users");

    return userRepository.findAll();
  }
}
