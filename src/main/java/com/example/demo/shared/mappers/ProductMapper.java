package com.example.demo.shared.mappers;

import com.example.demo.core.domain.Product;
import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import com.example.demo.infrastructure.repository.mysql.vo.ProductDetailVO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

//  @Mappings({})
  ProductEntity toEntity(Product product);

//  @Mappings({})
  Product toModel(ProductEntity entity);

  Product toModelV2(ProductDetailVO vo);
}
