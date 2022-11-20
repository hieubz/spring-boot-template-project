package com.example.demo.infrastructure.locks;

import com.example.demo.infrastructure.repository.mongo.entity.ProcessingProduct;
import com.example.demo.infrastructure.repository.mongo.primary.ProcessingProductRepository;
import com.example.demo.shared.exception.ConcurrentProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMongoLocker {

  private final ProcessingProductRepository processingProductRepository;

  public void lock(Long id) throws ConcurrentProcessingException {
    try {
      processingProductRepository.save(ProcessingProduct.builder().productId(id).build());
    } catch (DuplicateKeyException e) {
      throw new ConcurrentProcessingException("product " + id + " is processing by others");
    }
  }

  public void unlock(Long productId) {
    processingProductRepository.deleteByProductId(productId);
  }
}
