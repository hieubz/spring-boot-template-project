package com.example.demo.core.service;

import com.example.demo.infrastructure.repository.es.BookRepository;
import com.example.demo.infrastructure.repository.es.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookService")
@RequiredArgsConstructor
public class DefaultBookService implements BookService {

  @Autowired(required = false)
  private BookRepository bookRepository;

  @Override
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public void delete(Book book) {
    bookRepository.delete(book);
  }

  @Override
  public Book findById(String id) {
    return bookRepository.findById(id).orElse(null);
  }

  @Override
  public Iterable<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Page<Book> findByAuthor(String author, PageRequest pageRequest) {
    return bookRepository.findByAuthor(author, pageRequest);
  }

  @Override
  public List<Book> findByTitle(String title) {
    return bookRepository.findByTitle(title);
  }
}
