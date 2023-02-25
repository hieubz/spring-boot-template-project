package com.example.demo.core.service;

import com.example.demo.infrastructure.repository.es.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookService {

  Book save(Book book);

  void delete(Book book);

  Book findById(String id);

  Iterable<Book> findAll();

  Page<Book> findByAuthor(String author, PageRequest pageRequest);

  List<Book> findByTitle(String title);
}
