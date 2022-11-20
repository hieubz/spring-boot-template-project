package com.example.demo.infrastructure.repository.mysql.primary;

import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  @Modifying
  @Query(value = "update products set price = :price where id = :productId", nativeQuery = true)
  void updatePriceById(@Param("productId") long productId, @Param("price") float price);
}
