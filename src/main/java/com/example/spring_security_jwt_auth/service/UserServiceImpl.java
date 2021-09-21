package com.example.spring_security_jwt_auth.service;

import com.example.spring_security_jwt_auth.model.Role;
import com.example.spring_security_jwt_auth.model.User;
import com.example.spring_security_jwt_auth.repository.RoleRepository;
import com.example.spring_security_jwt_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public User saveUser(User user) {

    log.info("Store a user");
    String encodedPass = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPass);

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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findUserByUsername(username);

    if (user == null) {

      log.info("The user with a username = {} not found", username);
      throw new UsernameNotFoundException("The username not found");

    } else {

      log.info("Load a user with the username = {}", username);
    }

    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles()
        .forEach(
            role -> {
              authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), authorities);
  }
}
