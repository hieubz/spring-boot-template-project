package com.example.demo.infrastructure.repository.mongo.primary;

import com.example.demo.infrastructure.repository.mongo.entity.ProcessingProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProcessingProductRepository extends MongoRepository<ProcessingProduct, Long> {

  void deleteAllByProductIdIn(List<Long> ids);

  ProcessingProduct findByProductId(Long id);

  void deleteByProductId(Long id);
}
