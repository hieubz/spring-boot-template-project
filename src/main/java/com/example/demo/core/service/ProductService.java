package com.example.demo.core.service;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.core.adapter.ProductAdapter;
import com.example.demo.core.domain.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

  private final ProductAdapter productAdapter;

  public ProductService(@Qualifier("DefaultProductAdapter") ProductAdapter productAdapter) {
    this.productAdapter = productAdapter;
  }

  public void insertNewProduct(NewProductRequest request) {
    Product product = Product.builder().name(request.getName()).price(request.getPrice()).build();
    this.productAdapter.insertNewProduct(product);
  }

  public List<Product> loadAllProducts() {
    return this.productAdapter.loadAllProducts();
  }
}
