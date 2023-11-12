package com.example.demo.application.filter;

import com.example.demo.core.service.UserService;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import com.example.demo.shared.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Order(2)
public class VerifyFixedTokenFilter extends OncePerRequestFilter {

  @Value("${verified_tokens}")
  private Set<String> tokenSet;

  @Autowired private UserService userService;

  /** verify fixed tokens */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      if (verifyToken(getRequestToken(request))) {
        CustomUserDetails user = userService.loadDefaultUserForFixedTokenAuth();
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Invalid fixed token", e);
    }

    filterChain.doFilter(request, response);
  }

  private boolean verifyToken(Optional<String> token) {
    boolean verified = false;
    if (token.isPresent()) {
      verified = tokenSet.contains(token.get());
    }
    return verified;
  }

  private Optional<String> getRequestToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(AppConstants.FIXED_TOKEN_HEADER));
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getRequestURI().startsWith("/api");
  }
}
