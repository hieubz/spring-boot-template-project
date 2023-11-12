package com.example.demo.infrastructure.repository.mysql.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private Integer categoryId;

  private Float price;

  private Integer status;

  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime created;
}
