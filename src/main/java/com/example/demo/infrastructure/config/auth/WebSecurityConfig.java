package com.example.demo.infrastructure.config.auth;

import com.example.demo.application.filter.VerifyFixedTokenFilter;
import com.example.demo.application.filter.VerifyJwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${spring.main.web-application-type}")
  private String applicationType;

  private static final String[] AUTH_WHITELIST = {
    "/",
    "/api/login",
    // -- API document
    "/swagger-ui/**",
    "/v3/api-docs/**",
    // -- Prometheus
    "/actuator/**"
  };

  private static final String WEB_APPLICATION_TYPE = "servlet";

  @Autowired private AuthEntryPointJwt authEntryPointJwt;

  @Bean
  public VerifyJwtTokenFilter jwtAuthenticationFilter() {
    return new VerifyJwtTokenFilter();
  }

  @Bean
  public VerifyFixedTokenFilter fixedTokenAuthenticationFilter() {
    return new VerifyFixedTokenFilter();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // Get AuthenticationManager bean
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (applicationType.equals(WEB_APPLICATION_TYPE)) {
      http.cors().and().csrf().disable();
      http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated();
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.exceptionHandling().authenticationEntryPoint(authEntryPointJwt);
      http.addFilterBefore(
          fixedTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
      http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
  }
}
