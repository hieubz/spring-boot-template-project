package com.example.demo.infrastructure.repository.mysql.primary;

import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
