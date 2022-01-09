package com.example.demo.application;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.application.response.NewProductResponse;
import com.example.demo.core.domain.Product;
import com.example.demo.core.service.ProductService;
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
    Product product = Product.builder().name(request.getName()).price(request.getPrice()).build();
    productService.insertNewProduct(product);
    return new NewProductResponse("ok");
  }
}
