package com.example.demo.core.service;

import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.mapper.ProductMapper;
import com.example.demo.repository.primary.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper mapper;

  public void insertNewProduct(Product product) {
    this.productRepository.save(this.mapper.toEntity(product));
  }

  public List<Product> loadAllProducts() {
    return productRepository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
  }
}
