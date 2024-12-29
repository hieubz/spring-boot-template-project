package com.example.demo.core.service;

import com.example.demo.application.request.*;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.application.response.UpdatePriceResult;
import com.example.demo.core.adapter.ProductAdapter;
import com.example.demo.core.domain.Product;
import com.example.demo.core.domain.ProductStatus;
import com.example.demo.infrastructure.locks.ProductMongoLocker;
import com.example.demo.infrastructure.locks.ProductRedisLocker;
import com.example.demo.shared.annotation.TrackExecutionTime;
import com.example.demo.shared.exception.ConcurrentProcessingException;
import com.example.demo.shared.exception.EmptyRequestException;
import com.example.demo.shared.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component("ProductService")
@RequiredArgsConstructor
@Slf4j
public class DefaultProductService implements ProductService {

  private final ProductAdapter productAdapter;
  private final ProductRedisLocker productRedisLocker;
  private final ProductMongoLocker productMongoLocker;

  @Override
  public void insertNewProduct(NewProductRequest request) {
    log.info("> ProductService.insertNewProduct {}", request);
    Product product =
        Product.builder()
            .name(request.getName())
            .price(request.getPrice())
            .categoryId(request.getCategoryId())
            .status(ProductStatus.OK.id())
            .build();
    this.productAdapter.insertNewProduct(product);
  }

  @Override
  public void updateNewProduct(UpdateProductRequest request) throws ProductNotFoundException {
    Product product = this.productAdapter.findById(request.getId());
    if (product == null) throw new ProductNotFoundException("Product ID not found");
    product.setName(request.getName());
    product.setCategoryId(request.getCategoryId());
    product.setPrice(request.getPrice());
    this.productAdapter.updateNewProduct(product);
  }

  @Override
  public List<Product> loadAllProducts(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    return this.productAdapter.loadAllProducts(paging);
  }

  @Override
  @TrackExecutionTime
  public Product loadProductDetails(Long id) throws ProductNotFoundException {
    log.info("> ProductService.loadProductDetails id = {}", id);
    return this.productAdapter.loadProductDetails(id);
  }

  @Override
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

  @Override
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

  @Override
  public UpdatePriceResult updatePrice(UpdatePriceRequest req) {
    boolean race = false;
    try {
      Product product = productAdapter.findById(req.getProductId());
      if (product == null) {
        return UpdatePriceResult.builder().status(false).msg("The product was not existed!").build();
      }

      productMongoLocker.lock(req.getProductId());
      productAdapter.updatePrice(req.getProductId(), req.getPrice());
      return UpdatePriceResult.builder().status(true).msg("ok").build();
    } catch (ConcurrentProcessingException e) {
      race = true;
      log.info("> ConcurrentProcessingException {}", e.getMessage());
      return UpdatePriceResult.builder()
          .status(false)
          .msg("The product price is updating by others")
          .build();
    } catch (Exception e) {
      log.error("> Exception during price updates: ", e);
      return UpdatePriceResult.builder().status(false).msg("Updating price unsuccessful!").build();
    } finally {
      // unlock except data race exception
      if (!race) productMongoLocker.unlock(req.getProductId());
    }
  }

  @Override
  public List<Product> loadAllProductByFilter(GetAllProductRequest request) {
    return productAdapter.loadAllProductByFilter(request);
  }
}
