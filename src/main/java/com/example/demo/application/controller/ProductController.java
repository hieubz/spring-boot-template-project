package com.example.demo.application.controller;

import com.example.demo.application.request.NewProductRequest;
import com.example.demo.application.request.PriceCheckRequest;
import com.example.demo.application.response.FindProductResponse;
import com.example.demo.application.response.NewProductResponse;
import com.example.demo.application.response.PriceCheckResponse;
import com.example.demo.application.response.PriceCheckResult;
import com.example.demo.core.domain.Product;
import com.example.demo.core.service.ProductService;
import com.example.demo.infrastructure.events.NewProductEvent;
import com.example.demo.shared.exception.EmptyRequestException;
import com.example.demo.shared.exception.ProductNotFoundException;
import com.example.demo.shared.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

  private final ProductService productService;
  private final ApplicationEventPublisher applicationEventPublisher;

  @PostMapping(
      value = "/add",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public NewProductResponse addNewProduct(@RequestBody NewProductRequest request) {
    productService.insertNewProduct(request);
    applicationEventPublisher.publishEvent(
        new NewProductEvent(this, MDC.get(AppConstants.REQUEST_ID_KEY), request));
    return new NewProductResponse("ok");
  }

  @PostMapping(
      value = "/check-price-async",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PriceCheckResponse checkAsyncProductPrice(@RequestBody PriceCheckRequest request)
      throws EmptyRequestException, ExecutionException, InterruptedException {
    List<PriceCheckResult> results = productService.checkAsyncPrice(request);
    return PriceCheckResponse.builder().results(results).msg("ok").status(true).build();
  }

  @PostMapping(
      value = "/check-price",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PriceCheckResponse checkProductPrice(@RequestBody PriceCheckRequest request)
      throws EmptyRequestException, InterruptedException {
    List<PriceCheckResult> results = productService.checkPrice(request);
    return PriceCheckResponse.builder().results(results).msg("ok").status(true).build();
  }

  @GetMapping(value = "/get_details/{id}")
  public FindProductResponse getProductDetails(@PathVariable Long id)
      throws ProductNotFoundException {
    return FindProductResponse.builder()
        .product(productService.loadProductDetails(id))
        .status(true)
        .build();
  }

  @GetMapping(value = "/get-all")
  public ResponseEntity<List<Product>> getProductByPaging(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "20") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    List<Product> products = productService.loadAllProducts(pageNo, pageSize, sortBy);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
}
