package com.example.demo.application.filter;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestAndResponseLogFilter extends OncePerRequestFilter {

  private static final List<MediaType> VISIBLE_TYPES =
      Arrays.asList(
          MediaType.valueOf("text/*"),
          MediaType.APPLICATION_FORM_URLENCODED,
          MediaType.APPLICATION_JSON,
          MediaType.APPLICATION_XML,
          MediaType.valueOf("application/*+json"),
          MediaType.valueOf("application/*+xml"),
          MediaType.MULTIPART_FORM_DATA);
  /** List of HTTP headers whose values should not be logged. */
  private static final List<String> SENSITIVE_HEADERS =
      Arrays.asList("authorization", "proxy-authorization", "verify-token");

  private static final int REQUEST_LOG_TYPE = 0;
  private static final int RESPONSE_LOG_TYPE = 1;
  private final String PREFIX_API = "/api/";

  private static void logRequestHeader(ContentCachingRequestWrapper request, String prefix) {
    StringBuilder msg = new StringBuilder();
    String queryString = request.getQueryString();
    if (queryString == null) {
      msg.append(String.format("%s %s %s", prefix, request.getMethod(), request.getRequestURI()))
          .append("\n");
    } else {
      msg.append(
              String.format(
                  "%s %s %s?%s", prefix, request.getMethod(), request.getRequestURI(), queryString))
          .append("\n");
    }
    Collections.list(request.getHeaderNames())
        .forEach(
            headerName ->
                Collections.list(request.getHeaders(headerName))
                    .forEach(
                        headerValue -> {
                          if (isSensitiveHeader(headerName)) {
                            msg.append(
                                    String.format(
                                        "%s %s: %s",
                                        prefix, headerName, maskSensitiveData(headerValue)))
                                .append("\n");
                          } else {
                            msg.append(String.format("%s %s: %s", prefix, headerName, headerValue))
                                .append("\n");
                          }
                        }));
    MDC.put("headers", msg.toString());
  }

  private static void logRequestBody(ContentCachingRequestWrapper request, String prefix) {
    byte[] content = request.getContentAsByteArray();
    if (content.length > 0) {
      logContent(
          content,
          request.getContentType(),
          request.getCharacterEncoding(),
          prefix,
          REQUEST_LOG_TYPE);
    } else {
      // logging GET params
      log.info("> Request params: " + request.getQueryString());
    }
  }

  private static void logResponse(ContentCachingResponseWrapper response, String prefix) {
    int status = response.getStatus();
    MDC.put("http_status", status + "");
    byte[] content = response.getContentAsByteArray();
    if (content.length > 0) {
      logContent(
          content,
          response.getContentType(),
          response.getCharacterEncoding(),
          prefix,
          RESPONSE_LOG_TYPE);
    }
  }

  private static String maskSensitiveData(String input) {
    return input.replaceAll(".(?=.{8})", "*");
  }

  private static void logContent(
      byte[] content, String contentType, String contentEncoding, String prefix, int type) {

    StringBuilder msg = new StringBuilder();
    MediaType mediaType = MediaType.valueOf(contentType);
    boolean visible =
        VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
    if (visible) {
      try {
        String contentString = new String(content, contentEncoding);
        Stream.of(contentString.split("\r\n|\r|\n"))
            .forEach(line -> msg.append(prefix).append(" ").append(line).append("\n"));
      } catch (UnsupportedEncodingException e) {
        msg.append(String.format("%s [%d bytes content]", prefix, content.length)).append("\n");
      }
    } else {
      msg.append(String.format("%s [%d bytes content]", prefix, content.length)).append("\n");
    }

    log.info(msg.toString());
  }

  /**
   * Determine if a given header name should have its value logged.
   *
   * @param headerName HTTP header name.
   * @return True if the header is sensitive (i.e. its value should <b>not</b> be logged).
   */
  private static boolean isSensitiveHeader(String headerName) {
    return SENSITIVE_HEADERS.contains(headerName.toLowerCase());
  }

  private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
    if (request instanceof ContentCachingRequestWrapper) {
      return (ContentCachingRequestWrapper) request;
    } else {
      return new ContentCachingRequestWrapper(request);
    }
  }

  private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
    if (response instanceof ContentCachingResponseWrapper) {
      return (ContentCachingResponseWrapper) response;
    } else {
      return new ContentCachingResponseWrapper(response);
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (isAsyncDispatch(request)) {
      filterChain.doFilter(request, response);
    } else {
      doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
    }
  }

  protected void doFilterWrapped(
      ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response,
      FilterChain filterChain)
      throws ServletException, IOException {

    Stopwatch watch = Stopwatch.createStarted();
    try {
      beforeRequest(request, response);
      filterChain.doFilter(request, response);
    } finally {
      watch.stop();
      MDC.put("response_time", watch.elapsed().toMillis() + "");
      afterRequest(request, response);
      response.copyBodyToResponse();
    }
  }

  protected void beforeRequest(
      ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
    if (log.isInfoEnabled() && checkNeedToLog(request)) {
      logRequestHeader(request, "");
      MDC.put("endpoint", request.getRequestURI());
    }
  }

  protected void afterRequest(
      ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
    if (log.isInfoEnabled() && checkNeedToLog(request)) {
      logRequestBody(request, "");
      MDC.remove("headers");
      logResponse(response, "");
    }
  }

  private boolean checkNeedToLog(HttpServletRequest request) {
    return request.getRequestURI().startsWith(PREFIX_API);
  }
}
