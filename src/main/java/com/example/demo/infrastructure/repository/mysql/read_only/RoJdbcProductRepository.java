package com.example.demo.infrastructure.repository.mysql.read_only;

import com.example.demo.application.request.GetAllProductRequest;
import com.example.demo.infrastructure.repository.mysql.entity.ProductEntity;
import com.example.demo.shared.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoJdbcProductRepository {

  private final NamedParameterJdbcTemplate roJdbcTemplate;

  public List<ProductEntity> findAllByCriteria(GetAllProductRequest criteria) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("categoryIds", criteria.getCategoryIds());
    String sql = "select * from products where category_id in (:categoryIds)";
    sql = this.appendConditionByCriteria(sql, criteria, params);
    sql = this.appendPagingSQL(sql, criteria, params);
    log.info("JdbcTemplate is executing: {}", sql);
    return roJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ProductEntity.class));
  }

  private String appendPagingSQL(
      String sql, GetAllProductRequest criteria, MapSqlParameterSource params) {
    StringBuilder sb = new StringBuilder(sql);
    Integer limit = Objects.nonNull(criteria.getLimit()) ? criteria.getLimit() : AppConstants.DEFAULT_QUERY_LIMIT;
    int page = Objects.nonNull(criteria.getPage()) ? criteria.getPage() : 1;
    sb.append(" limit :limit");
    params.addValue("limit", limit);
    sb.append(" offset :offset");
    params.addValue("offset", (page - 1) * limit);
    return sb.toString();
  }

  private String appendConditionByCriteria(
      String sql, GetAllProductRequest criteria, MapSqlParameterSource params) {
    StringBuilder sb = new StringBuilder(sql);
    if (Objects.nonNull(criteria.getMinPrice())) {
      sb.append(" and price >= :minPrice");
      params.addValue("minPrice", criteria.getMinPrice());
    }
    if (Objects.nonNull(criteria.getMaxPrice())) {
      sb.append(" and price <= :maxPrice");
      params.addValue("maxPrice", criteria.getMaxPrice());
    }
    if (Objects.nonNull(criteria.getStatus())) {
      sb.append(" and status = :status");
      params.addValue("status", criteria.getStatus());
    }
    return sb.toString();
  }
}
