package com.example.demo.core.adapter;

import com.example.demo.core.domain.Product;

import java.util.List;

public interface ProductAdapter {
  List<Product> loadAllProducts();

  void insertNewProduct(Product product);
}
