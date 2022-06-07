package com.example.demo.core.service;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.application.request.PriceCheckRequest;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.domain.Product;
import com.example.demo.shared.exception.EmptyRequestException;
import com.example.demo.shared.exception.ProductNotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductService {

  void insertNewProduct(NewProductRequest request);

  List<Product> loadAllProducts(Integer pageNo, Integer pageSize, String sortBy);

  Product loadProductDetails(Long id) throws ProductNotFoundException;

  List<PriceCheckResult> checkAsyncPrice(PriceCheckRequest request)
      throws EmptyRequestException, ExecutionException, InterruptedException;

  List<PriceCheckResult> checkPrice(PriceCheckRequest request)
      throws EmptyRequestException, InterruptedException;
}
