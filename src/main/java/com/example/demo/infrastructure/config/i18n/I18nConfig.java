package com.example.demo.infrastructure.config.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class I18nConfig implements WebMvcConfigurer {

  private final CustomLocaleResolver customLocaleResolver;

  // Defining MessageSource as a bean for Spring to manage
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("i18n/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  /**
   * - Default LocaleResolver of Spring Boot app is AcceptHeaderLocaleResolver.
   * - Note: cannot use both LocaleChangeInterceptor and AcceptHeaderLocaleResolver simultaneously in
   * a Spring Boot application
   */
  //  @Bean
  //  public LocaleResolver localeResolver() {
  //    // You can use SessionLocaleResolver or CookieLocaleResolver as alternatives based on
  //    // requirements
  //    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
  //    localeResolver.setDefaultLocale(Locale.ENGLISH); // default locale
  //    return localeResolver;
  //  }

  /**
   * The way to achieve both header-based locale resolution and parameter-based locale switching by
   * implementing a custom LocaleResolver
   */
  @Bean
  public LocaleResolver localeResolver() {
    return customLocaleResolver;
  }

  /**
   * Intercepts requests and changes the locale based on a specific parameter (lang in this case).
   * Ex: http://localhost:8080/?lang=en, http://localhost:8080/?lang=fr
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang"); // the request param to change language
    return lci;
  }

  /**
   * The interceptor is added to the application's interceptor registry to intercept incoming HTTP
   * requests.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
}
