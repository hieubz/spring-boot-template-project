package com.example.demo.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

public final class JpaAdditionalPropertiesHelper {
  public static Map<String, String> additionalProperties() {
    Map<String, String> properties = new HashMap<>();
    properties.put(
        "hibernate.physical_naming_strategy",
        "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
    properties.put(
        "hibernate.implicit_naming_strategy",
        "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    return properties;
  }
}
