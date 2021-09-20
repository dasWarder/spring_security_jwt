package com.example.spring_security_jwt_auth.repository;

import com.example.spring_security_jwt_auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findRoleByName(String name);
}
