package com.example.demo.core.adapter;

import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.exception.ProductNotFoundException;

import java.util.List;

public interface ProductAdapter {
  List<Product> loadAllProducts();

  void insertNewProduct(Product product);

  Product loadProductDetails(Long id) throws ProductNotFoundException;
}
