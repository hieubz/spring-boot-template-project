package com.example.demo.core.service;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.application.request.PriceCheckRequest;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.adapter.ProductAdapter;
import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.exception.EmptyRequestException;
import com.example.demo.infrastructure.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component("ProductService")
@Slf4j
public class ProductService {

  private final ProductAdapter productAdapter;

  public ProductService(@Qualifier("DefaultProductAdapter") ProductAdapter productAdapter) {
    this.productAdapter = productAdapter;
  }

  public void insertNewProduct(NewProductRequest request) {
    log.info("> ProductService.insertNewProduct {}", request);
    Product product = Product.builder().name(request.getName()).price(request.getPrice()).build();
    this.productAdapter.insertNewProduct(product);
  }

  public List<Product> loadAllProducts() {
    return this.productAdapter.loadAllProducts();
  }

  public Product loadProductDetails(Long id) throws ProductNotFoundException {
    log.info("> ProductService.loadProductDetails id = {}", id);
    return this.productAdapter.loadProductDetails(id);
  }

  public List<PriceCheckResult> checkAsyncPrice(PriceCheckRequest request)
      throws EmptyRequestException, ExecutionException, InterruptedException {
    log.info("> ProductService.checkAsyncPrice {}", request);
    if (Objects.isNull(request.getProducts()) || request.getProducts().isEmpty()) {
      throw new EmptyRequestException();
    }
    List<CompletableFuture<PriceCheckResult>> futures =
        new ArrayList<>(request.getProducts().size());
    for (Product p : request.getProducts()) {
      CompletableFuture<PriceCheckResult> future = this.productAdapter.checkAsyncPrice(p);
      futures.add(future);
    }
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
    List<PriceCheckResult> results = new ArrayList<>();
    for (CompletableFuture<PriceCheckResult> future : futures) {
      results.add(future.get());
    }
    return results;
  }

  public List<PriceCheckResult> checkPrice(PriceCheckRequest request)
      throws EmptyRequestException, InterruptedException {
    log.info("> ProductService.checkPrice {}", request);
    if (Objects.isNull(request.getProducts()) || request.getProducts().isEmpty()) {
      throw new EmptyRequestException();
    }

    List<PriceCheckResult> results = new ArrayList<>();
    for (Product p : request.getProducts()) {
      results.add(this.productAdapter.checkPrice(p));
    }
    return results;
  }
}
