package com.example.demo.core.service;

import com.example.demo.core.adapter.DemoAdapterForTest;
import com.example.demo.core.domain.Post;
import com.example.demo.shared.utils.UtilForTest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Use only for Unit Test common cases
 */
@Component
@RequiredArgsConstructor
public class DemoServiceForTest {

  private final DemoAdapterForTest adapter;

  public String feature1(String s) {
    adapter.voidMethod(s);
    UtilForTest.voidStaticMethod();
    return UtilForTest.nonVoidStaticMethod(s);
  }

  public void feature2(String s) {
    try {
      String st = UtilForTest.nonVoidStaticMethod(s);
    } finally {
      UtilForTest.voidStaticMethod();
    }
  }

  public void feature3(String s) {
    UtilForTest.voidStaticMethod2(s);
    UtilForTest.voidStaticMethod();
  }

  public String feature4() {
    return privateMethod();
  }

  public void feature5() {
    privateMethod2("");
  }

  public void feature6(String s) {
    UtilForTest.voidStaticMethod2(s);
  }

  public void feature7() {
    Post post = new Post();
    post.setBody("");
  }

  private String privateMethod() {
    return "";
  }

  private void privateMethod2(String str) {
    String s = privateMethod();
  }
}
