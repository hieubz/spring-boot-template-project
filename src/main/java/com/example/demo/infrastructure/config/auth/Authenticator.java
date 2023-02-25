package com.example.demo.infrastructure.config.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("Authenticator")
public class Authenticator {

  public Long getUserId() {
    CustomUserDetails userDetails = getAuthUserDetails();
    return userDetails != null && userDetails.getUserId() != null ? userDetails.getUserId() : -1L;
  }

  public String getUsername() {
    CustomUserDetails userDetails = getAuthUserDetails();
    return userDetails != null ? userDetails.getUsername() : "";
  }

  public CustomUserDetails getAuthUserDetails() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof CustomUserDetails) {
      return (CustomUserDetails) principal;
    }
    return null;
  }
}
