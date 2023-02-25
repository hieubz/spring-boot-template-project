package com.example.demo.infrastructure.repository.mysql.read_only;

import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import com.example.demo.infrastructure.repository.mysql.vo.ProductDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RoProductRepository extends JpaRepository<ProductEntity, Long> {

  @Query(
      value =
          "SELECT \n"
              + "    p.id,\n"
              + "    p.`name`,\n"
              + "    p.price,\n"
              + "    p.created, \n"
              + "    pd.quantity,\n"
              + "    pd.image_path as imagePath\n"
              + "FROM\n"
              + "    demo.products p\n"
              + "        LEFT JOIN\n"
              + "    product_details pd ON p.id = pd.product_id\n"
              + "WHERE\n"
              + "    p.id = :id",
      nativeQuery = true)
  ProductDetailVO getProductDetailsById(@Param("id") Long id);
}
