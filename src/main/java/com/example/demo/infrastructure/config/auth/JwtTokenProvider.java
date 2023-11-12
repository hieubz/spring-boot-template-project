package com.example.demo.infrastructure.config.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  @Value("${com.example.demo.jwtSecret}")
  private String jwtSecret;

  @Value("${com.example.demo.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Long userId) {
    return Jwts.builder()
        .subject(userId.toString())
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
        .compact();
  }

  public Claims getJwtTokenClaim(String jwt) {
    if (Objects.isNull(jwt)) return null;
    try {
      return Jwts.parser()
          .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
          .build()
          .parseSignedClaims(jwt)
          .getPayload();
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token was expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return null;
  }
}
