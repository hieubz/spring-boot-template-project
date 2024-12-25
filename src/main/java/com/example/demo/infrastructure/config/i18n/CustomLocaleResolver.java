package com.example.demo.infrastructure.config.i18n;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
public class CustomLocaleResolver implements LocaleResolver {

  private static final String LANG_PARAM = "lang";

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    // Check for 'lang' parameter in the request
    String lang = request.getParameter(LANG_PARAM);
    if (lang != null && !lang.isEmpty()) {
      return Locale.forLanguageTag(lang);
    }

    // Fallback to 'Accept-Language' header if no param is provided
    Locale locale = request.getLocale();
    return locale != null ? locale : Locale.getDefault();
  }

  @Override
  public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    // This method can be left empty or used to implement additional logic if needed
    // Since AcceptHeaderLocaleResolver is stateless, we'll keep it empty
  }
}
