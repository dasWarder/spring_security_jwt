package com.example.spring_security_jwt_auth.repository;

import com.example.spring_security_jwt_auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findUserByUsername(String username);
}
