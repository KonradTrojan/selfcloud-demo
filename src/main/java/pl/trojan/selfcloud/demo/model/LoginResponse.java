package pl.trojan.selfcloud.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoginResponse {

  private String token;

  private long expiresIn;

  // Getters and setters...
}