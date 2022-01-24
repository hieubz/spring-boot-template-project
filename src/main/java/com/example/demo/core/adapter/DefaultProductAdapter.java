package com.example.demo.core.adapter;

import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.config.CacheConfig;
import com.example.demo.infrastructure.exception.ProductNotFoundException;
import com.example.demo.infrastructure.mapper.ProductMapper;
import com.example.demo.repository.entity.ProductEntity;
import com.example.demo.repository.primary.ProductRepository;
import com.example.demo.repository.read_only.RoProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component("DefaultProductAdapter")
@RequiredArgsConstructor
public class DefaultProductAdapter implements ProductAdapter {

  private final ProductRepository productRepository;
  private final RoProductRepository roProductRepository;
  private final ProductMapper mapper;

  @Override
  public List<Product> loadAllProducts() {
    return roProductRepository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
  }

  @Override
  public void insertNewProduct(Product product) {
    this.productRepository.save(this.mapper.toEntity(product));
  }

  @Override
  @Cacheable(
      cacheNames = "productDetails",
      unless = "#result == null",
      cacheManager = CacheConfig.CACHE_REDIS)
  public Product loadProductDetails(Long id) throws ProductNotFoundException {
    Product product = mapper.toModelV2(roProductRepository.getProductDetailsById(id));
    if (Objects.isNull(product)) throw new ProductNotFoundException("Product Not Found");
    return product;
  }

  @Async("threadPoolTaskExecutor")
  @Override
  public CompletableFuture<PriceCheckResult> checkAsyncPrice(Product p)
      throws InterruptedException {
    Optional<ProductEntity> productEntity = productRepository.findById(p.getId());
    if (productEntity.isEmpty() || !Objects.equals(productEntity.get().getPrice(), p.getPrice())) {
      return CompletableFuture.completedFuture(
          PriceCheckResult.builder().id(p.getId()).status(false).build());
    }

    TimeUnit.SECONDS.sleep(1); // simulate for doing smt
    return CompletableFuture.completedFuture(
        PriceCheckResult.builder().id(p.getId()).status(true).build());
  }

  @Override
  public PriceCheckResult checkPrice(Product p) throws InterruptedException {
    Optional<ProductEntity> productEntity = productRepository.findById(p.getId());
    if (productEntity.isEmpty() || !Objects.equals(productEntity.get().getPrice(), p.getPrice())) {
      return PriceCheckResult.builder().id(p.getId()).status(false).build();
    }

    TimeUnit.SECONDS.sleep(1); // simulate for doing smt
    return PriceCheckResult.builder().id(p.getId()).status(true).build();
  }
}
