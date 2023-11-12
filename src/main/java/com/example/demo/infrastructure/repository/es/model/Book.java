package com.example.demo.infrastructure.repository.es.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Id;

@Document(indexName = "hieubz")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  @Id private String id;
  private String title;
  private String author;
  private String releaseDate;

  @Override
  public String toString() {
    return "Book{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", releaseDate='" + releaseDate + '\'' +
            '}';
  }
}
