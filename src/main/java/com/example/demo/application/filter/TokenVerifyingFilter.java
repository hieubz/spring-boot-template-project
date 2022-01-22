package com.example.demo.application.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@Order(1)
public class TokenVerifyingFilter extends OncePerRequestFilter {

  /** verify fixed tokens */
  private final String VERIFY_TOKEN_HEADER = "Verify-Token";

  @Value("${verified_tokens}")
  private Set<String> tokenSet;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      if (verifyToken(getRequestToken(request))) {
        filterChain.doFilter(request, response);
      } else {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    } catch (Exception e) {
      log.error("Invalid token", e);
    }
  }

  private boolean verifyToken(Optional<String> token) {
    boolean verified = false;
    if (token.isPresent()) {
      verified = tokenSet.contains(token.get());
    }
    return verified;
  }

  private Optional<String> getRequestToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(VERIFY_TOKEN_HEADER));
  }
}
