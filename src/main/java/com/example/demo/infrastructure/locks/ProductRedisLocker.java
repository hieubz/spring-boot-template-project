package com.example.demo.infrastructure.locks;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("ProductRedisLocker")
public class ProductRedisLocker extends BaseRedisLocker<Long> {

  @PostConstruct
  public void init() {
    setTtl(10);
    setKeyType("product");
    setBucketKey("processing_products");
    initBucket();
  }
}
