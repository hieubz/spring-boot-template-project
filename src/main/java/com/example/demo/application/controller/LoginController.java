package com.example.demo.application.controller;

import com.example.demo.application.request.LoginRequest;
import com.example.demo.application.response.BaseResponse;
import com.example.demo.application.response.LoginResponse;
import com.example.demo.core.service.UserService;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import com.example.demo.infrastructure.config.auth.JwtTokenProvider;
import com.example.demo.shared.constants.AppConstants;
import com.example.demo.shared.utils.EncryptedPasswordUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@Tag(name = "Login Controller")
@RequiredArgsConstructor
public class LoginController {

  @Autowired AuthenticationManager authenticationManager;

  private final JwtTokenProvider tokenProvider;

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    // verify username and password
    CustomUserDetails userDetails = userService.getUserDetailByUsername(request.getUsername());
    if (Objects.isNull(userDetails)
        || !EncryptedPasswordUtils.getPasswordEncoder()
            .matches(request.getPassword(), userDetails.getEncryptedPassword())) {
      return LoginResponse.builder().success(false).message("invalid username/password").build();
    }

    // gen and return jwt to client
    String jwt = tokenProvider.generateJwtToken(userDetails.getUserId());
    return LoginResponse.builder()
        .success(true)
        .accessToken(jwt)
        .tokenType(AppConstants.TOKEN_TYPE)
        .build();
  }

  @GetMapping("/test-valid-jwt")
  public BaseResponse testValidJwt() {
    return BaseResponse.builder().success(true).message("You are using a valid JWT").build();
  }
}
