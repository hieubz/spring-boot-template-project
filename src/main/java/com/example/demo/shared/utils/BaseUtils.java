package com.example.demo.shared.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class BaseUtils {

  public static String htmlToText(String htmlFilePath) {
    if (!StringUtils.hasLength(htmlFilePath)) return null;
    try {
      InputStream inputStream = BaseUtils.class.getClassLoader().getResourceAsStream(htmlFilePath);
      if (inputStream == null) return null;
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
      StringWriter writer = new StringWriter();
      IOUtils.copy(reader, writer);
      return writer.toString();
    } catch (Exception e) {
      log.error(
          "> ERROR BaseUtils.htmlToString Something went wrong when converting HTML file: {}",
          e.getMessage());
    }
    return null;
  }
}
