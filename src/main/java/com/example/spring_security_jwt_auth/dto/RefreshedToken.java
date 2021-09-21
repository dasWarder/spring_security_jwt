package com.example.spring_security_jwt_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshedToken {

  private String refreshToken;

  private String token;
}
