package com.example.demo.infrastructure.mapper;

import com.example.demo.core.domain.Product;
import com.example.demo.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

  @Mappings({})
  ProductEntity toEntity(Product product);

  @Mappings({})
  Product toModel(ProductEntity entity);
}
