package com.example.demo.application.request;

import lombok.Value;

@Value
public class NewProductRequest {
  String name;
  Float price;
}
