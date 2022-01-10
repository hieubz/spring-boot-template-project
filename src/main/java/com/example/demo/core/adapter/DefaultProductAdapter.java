package com.example.demo.core.adapter;

import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.mapper.ProductMapper;
import com.example.demo.repository.primary.ProductRepository;
import com.example.demo.repository.read_only.RoProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
}
