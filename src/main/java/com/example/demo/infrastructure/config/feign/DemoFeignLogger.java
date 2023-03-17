package com.example.demo.infrastructure.config.feign;

import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;

/**
 * a custom FeignLogger that extends Feign Logger class
 * to collect all feign logs as one message
 */
@Slf4j
public class DemoFeignLogger extends feign.Logger {

  private final String MDC_CALL_TO_KEY = "call_to";
  private final String MDC_REQUEST_BODY_KEY = "request_body";
  private final String MDC_RESPONSE_TIME_KEY = "response_time";
  private final String MDC_RESPONSE_STATUS_KEY = "response_status";

  @Override
  protected void logRequest(String configKey, Level logLevel, Request request) {
    List<String> logs = new ArrayList<>();
    if (request.body() != null) {
      if (logLevel.ordinal() >= Level.FULL.ordinal()) {
        String bodyText =
            request.charset() != null ? new String(request.body(), request.charset()) : null;
        logs.add(formatV2(configKey, "%s", bodyText != null ? bodyText : "Binary data"));
      }
    }
    // log HTTP method and URL client
    MDC.put(MDC_CALL_TO_KEY, request.httpMethod().name() + " " + request.url());
    // log request body
    // collect all feign logs as one message
    MDC.put(MDC_REQUEST_BODY_KEY, String.join("\n", logs));
  }

  @Override
  protected Response logAndRebufferResponse(
      String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
    List<String> logs = new ArrayList<>();
    int status = response.status();
    // customize more readable log fields
    MDC.put(MDC_RESPONSE_TIME_KEY, elapsedTime + "");
    MDC.put(MDC_RESPONSE_STATUS_KEY, status + "");

    try {
      int bodyLength = 0;
      if (response.body() != null && !(status == 204 || status == 205)) {
        // HTTP 204 No Content "...response MUST NOT include a message-body"
        // HTTP 205 Reset Content "...response MUST NOT include an entity"
        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
        bodyLength = bodyData.length;
        if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
          logs.add(formatV2("%s", decodeOrDefault(bodyData, UTF_8, "Binary data")));
        }
        log.info(String.join("\n", logs));
        return response.toBuilder().body(bodyData).build();
      } else {
        logs.add(format(configKey, "<--- END HTTP (%s-byte body)", bodyLength));
      }
      // collect all feign logs as one message
      log.info(String.join("\n", logs));
      return response;
    } finally {
      cleanMDCContext();
    }
  }

  /**
   *  Clear MDC context after logging each request
   *  because we will add new data in the next request
   */
  private void cleanMDCContext() {
    MDC.remove(MDC_CALL_TO_KEY);
    MDC.remove(MDC_REQUEST_BODY_KEY);
    MDC.remove(MDC_RESPONSE_TIME_KEY);
    MDC.remove(MDC_RESPONSE_STATUS_KEY);
  }

  @Override
  protected void log(String configKey, String format, Object... args) {
    // Not using SLF4J's support for parameterized messages (even though it would be more efficient)
    // because it would require the incoming message formats to be SLF4J-specific.
    log.info(format(configKey, format, args));
  }

  private String format(String configKey, String format, Object... args) {
    return String.format(methodTag(configKey) + format, args);
  }

  private String formatV2(String format, Object... args) {
    return String.format(format, args);
  }
}
