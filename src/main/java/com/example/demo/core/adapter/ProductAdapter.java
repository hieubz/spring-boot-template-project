package com.example.demo.core.adapter;

import com.example.demo.application.request.GetAllProductRequest;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.domain.Product;
import com.example.demo.shared.exception.ProductNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductAdapter {
  List<Product> loadAllProducts(Pageable paging);

  List<Product> loadAllProductByFilter(GetAllProductRequest getAllProductRequest);

  void insertNewProduct(Product product);

  void updateNewProduct(Product product);

  Product loadProductDetails(Long id) throws ProductNotFoundException;

  CompletableFuture<PriceCheckResult> checkAsyncPrice(Product product) throws InterruptedException;

  PriceCheckResult checkPrice(Product product) throws InterruptedException;

  void updatePrice(long productId, float price);

  Product findById(long productId);
}
