package com.example.demo.application.controller;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.application.response.FindProductResponse;
import com.example.demo.application.response.NewProductResponse;
import com.example.demo.core.service.ProductService;
import com.example.demo.infrastructure.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

  private final ProductService productService;

  @PostMapping(
      value = "/add",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public NewProductResponse addNewProduct(@RequestBody NewProductRequest request) {
    productService.insertNewProduct(request);
    return new NewProductResponse("ok");
  }

  @GetMapping(value = "/get_details/{id}")
  public FindProductResponse getProductDetails(@PathVariable Long id)
      throws ProductNotFoundException {
    return FindProductResponse.builder()
        .product(productService.loadProductDetails(id))
        .status(true)
        .build();
  }
}
