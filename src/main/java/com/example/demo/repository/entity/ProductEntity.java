package com.example.demo.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity extends BaseEntity {

  @Id
  private long id;

  private String name;

  private Float price;

  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  @CreationTimestamp
  private LocalDateTime created;
}
