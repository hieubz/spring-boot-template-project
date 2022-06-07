package com.example.demo.core.adapter;

import com.example.demo.infrastructure.config.auth.CustomUserDetails;

public interface UserAdapter {
  CustomUserDetails getUserDetailByUserId(Long userId);

  CustomUserDetails getUserDetailByUsername(String username);
}
