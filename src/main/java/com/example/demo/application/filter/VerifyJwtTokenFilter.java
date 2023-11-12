package com.example.demo.application.filter;

import com.example.demo.core.service.UserService;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import com.example.demo.infrastructure.config.auth.JwtTokenProvider;
import com.example.demo.shared.constants.AppConstants;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Order(3)
public class VerifyJwtTokenFilter extends OncePerRequestFilter {

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      Claims claims = jwtTokenProvider.getJwtTokenClaim(jwt);
      if (Objects.nonNull(claims)) {
        Long userId = Long.parseLong(claims.getSubject());
        CustomUserDetails user = userService.getUserDetailByUserId(userId);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Failed to verify jwt", e);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader(AppConstants.JWT_TOKEN_HEADER);
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }
}
