package com.example.demo.core.service;

import com.example.demo.infrastructure.repository.es.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// Note: Disable due to no ElasticSearch on local
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BookServiceTest {
//
//  @Autowired private BookService bookService;
//
//  @Test
//  public void testFindByAuthor() {
//
//    List<Book> bookList = new ArrayList<>();
//
//    bookList.add(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
//    bookList.add(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
//    bookList.add(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
//    bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Rambabu Posa", "01-APR-2017"));
//    bookList.add(new Book("1008", "Spring Boot + MongoDB", "hieubz", "25-FEB-2017"));
//
//    for (Book book : bookList) {
//      bookService.save(book);
//    }
//
//    Page<Book> byAuthor = bookService.findByAuthor("Rambabu Posa", PageRequest.of(0, 10));
//    byAuthor.toList().forEach(b -> System.out.println(b.toString()));
//    assertEquals(4L, byAuthor.getTotalElements());
//    System.out.println("------------------------------------");
//
//    Page<Book> byAuthor2 = bookService.findByAuthor("hieubz", PageRequest.of(0, 10));
//    byAuthor2.toList().forEach(b -> System.out.println(b.toString()));
//    assertEquals(1L, byAuthor2.getTotalElements());
//  }
//}
