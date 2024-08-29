package com.example.demo.core.adapter;

import com.example.demo.application.request.GetAllProductRequest;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.config.CacheConfig;
import com.example.demo.infrastructure.repository.mongo.entity.DemoLog;
import com.example.demo.infrastructure.repository.mongo.primary.DemoLogRepository;
import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import com.example.demo.infrastructure.repository.mysql.primary.ProductRepository;
import com.example.demo.infrastructure.repository.mysql.read_only.RoJdbcProductRepository;
import com.example.demo.infrastructure.repository.mysql.read_only.RoProductRepository;
import com.example.demo.shared.constants.AppConstants;
import com.example.demo.shared.exception.ProductNotFoundException;
import com.example.demo.shared.mappers.BaseModelMapper;
import com.example.demo.shared.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component("DefaultProductAdapter")
@RequiredArgsConstructor
public class DefaultProductAdapter implements ProductAdapter {

  private final ProductRepository productRepository;
  private final RoProductRepository roProductRepository;
  private final RoJdbcProductRepository roJdbcProductRepository;
  private final DemoLogRepository logRepository;
  private final ProductMapper mapper;

  @Override
  public List<Product> loadAllProducts(Pageable paging) {
    DemoLog requestLog = DemoLog.builder().requestId(MDC.get(AppConstants.REQUEST_ID_KEY)).build();
    logRepository.insert(requestLog);
    return BaseModelMapper.mapList(roProductRepository.findAll(paging).toList(), Product.class);
  }

  @Override
  public List<Product> loadAllProductByFilter(GetAllProductRequest request) {
    return BaseModelMapper.mapList(roJdbcProductRepository.findAllByCriteria(request), Product.class);
  }

  @Override
  public void insertNewProduct(Product product) {
    this.productRepository.save(BaseModelMapper.mapper(product, ProductEntity.class));
  }

  @Override
  public void updateNewProduct(Product product) {
    this.productRepository.save(BaseModelMapper.mapper(product, ProductEntity.class));
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

  @Override
  public void updatePrice(long productId, float price) {
    productRepository.updatePriceById(productId, price);
  }

  @Override
  public Product findById(long id) {
    Optional<ProductEntity> entity = roProductRepository.findById(id);
    return entity.isEmpty() ? null : BaseModelMapper.mapper(entity.get(), Product.class);
  }
}
