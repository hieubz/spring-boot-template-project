package com.example.demo.core.service;

import com.example.demo.core.adapter.UserAdapter;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserAdapter userAdapter;

  @Override
  public CustomUserDetails getUserDetailByUserId(Long userId) {
    return userAdapter.getUserDetailByUserId(userId);
  }

  @Override
  public CustomUserDetails getUserDetailByUsername(String username) {
    return userAdapter.getUserDetailByUsername(username);
  }

  @Override
  public CustomUserDetails loadDefaultUserForFixedTokenAuth() {
    return CustomUserDetails.builder().userId(0L).username("default").roles(List.of("USER")).build();
  }

  /** Use only for Unit Test common cases */
  public void voidMethodForUnitTesting() {
    String s = feature2();
  }

  /** Use only for Unit Test common cases */
  public void voidMethodForUnitTesting2() {
  }

  /** Use only for Unit Test common cases */
  public void feature1() {
    voidMethodForUnitTesting();
    voidMethodForUnitTesting2();
    feature2();
  }

  /** Use only for Unit Test common cases */
  public String feature2() {
    return "";
  }
}
