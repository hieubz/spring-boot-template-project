package com.example.demo.shared.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptedPasswordUtils {

  public static String encryptPassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }

  public static PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
