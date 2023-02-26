package com.example.demo.core.adapter;

import com.example.demo.infrastructure.config.CacheConfig;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import com.example.demo.shared.utils.EncryptedPasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultUserAdapter implements UserAdapter {

  @Override
  @Cacheable(
      cacheNames = "getUserDetailByUserId",
      unless = "#result == null",
      cacheManager = CacheConfig.CACHE_REDIS)
  public CustomUserDetails getUserDetailByUserId(Long userId) {
    // TODO: query from database, join users and user_roles tables
    return CustomUserDetails.builder()
        .userId(123L)
        .username("hieupd")
        .encryptedPassword(EncryptedPasswordUtils.encryptPassword("test"))
        .departmentId(1)
        .tel("0364139622")
        .roles(List.of("USER", "DBA"))
        .build();
  }

  @Override
  @Cacheable(
      cacheNames = "getUserDetailByUsername",
      unless = "#result == null",
      cacheManager = CacheConfig.CACHE_REDIS)
  public CustomUserDetails getUserDetailByUsername(String username) {
    // TODO: get from database
    return CustomUserDetails.builder()
        .userId(123L)
        .username("hieupd")
        .encryptedPassword(EncryptedPasswordUtils.encryptPassword("test"))
        .departmentId(1)
        .tel("0364139622")
        .build();
  }
}
