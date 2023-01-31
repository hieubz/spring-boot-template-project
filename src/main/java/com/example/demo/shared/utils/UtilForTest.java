package com.example.demo.shared.utils;

/**
 * Use only for Unit Test common cases
 */
public class UtilForTest {

  public static String nonVoidStaticMethod(String s) {
    privateVoidStaticMethod();
    return privateStaticMethod();
  }

  public static void voidStaticMethod() {
    privateVoidStaticMethod();
    String s = privateStaticMethod();
  }

  public static void voidStaticMethod2(String s) {
    privateVoidStaticMethod();
  }

  private static void privateVoidStaticMethod() {
    privateVoidStaticMethod2();
  }

  private static void privateVoidStaticMethod2() {
  }

  private static String privateStaticMethod() {
    return "";
  }
}
