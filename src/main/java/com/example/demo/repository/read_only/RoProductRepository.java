package com.example.demo.repository.read_only;

import com.example.demo.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RoProductRepository extends JpaRepository<ProductEntity, Long> {}
