package com.example.demo.core.service;

import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

  CustomUserDetails getUserDetailByUserId(Long userId);

  CustomUserDetails getUserDetailByUsername(String username);

  CustomUserDetails loadDefaultUserForFixedTokenAuth();
}
