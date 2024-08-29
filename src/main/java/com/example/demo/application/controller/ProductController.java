package com.example.demo.application.controller;

import com.example.demo.application.request.*;
import com.example.demo.application.response.*;
import com.example.demo.core.domain.Product;
import com.example.demo.core.service.ProductService;
import com.example.demo.infrastructure.events.NewProductEvent;
import com.example.demo.shared.constants.AppConstants;
import com.example.demo.shared.exception.EmptyRequestException;
import com.example.demo.shared.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller")
@RequiredArgsConstructor
public class ProductController extends BaseController {

  private final ProductService productService;
  private final ApplicationEventPublisher applicationEventPublisher;

  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public NewProductResponse addNewProduct(@RequestBody NewProductRequest request) {
    productService.insertNewProduct(request);
    applicationEventPublisher.publishEvent(
        new NewProductEvent(this, MDC.get(AppConstants.REQUEST_ID_KEY), request));
    return new NewProductResponse("ok");
  }

  @PutMapping(
          consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
          name = AppConstants.FIXED_TOKEN_HEADER,
          required = true,
          in = ParameterIn.HEADER,
          example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public NewProductResponse updateProduct(@Valid @RequestBody UpdateProductRequest request) throws ProductNotFoundException {
    productService.updateNewProduct(request);
    return new NewProductResponse("ok");
  }

  @PostMapping(
      value = "/check-price-async",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public PriceCheckResponse checkAsyncProductPrice(@RequestBody PriceCheckRequest request)
      throws EmptyRequestException, ExecutionException, InterruptedException {
    List<PriceCheckResult> results = productService.checkAsyncPrice(request);
    return PriceCheckResponse.builder().results(results).message("ok").success(true).build();
  }

  @PostMapping(
      value = "/check-price",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public PriceCheckResponse checkProductPrice(@RequestBody PriceCheckRequest request)
      throws EmptyRequestException, InterruptedException {
    List<PriceCheckResult> results = productService.checkPrice(request);
    return PriceCheckResponse.builder().results(results).message("ok").success(true).build();
  }

  @PostMapping(
      value = "/update-price",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public BaseResponse updatePrice(@RequestBody UpdatePriceRequest request) throws Exception {
    UpdatePriceResult result = productService.updatePrice(request);
    return BaseResponse.builder().success(result.getStatus()).message(result.getMsg()).build();
  }

  @GetMapping(value = "/{id}")
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public FindProductResponse getProductDetails(@PathVariable Long id)
      throws ProductNotFoundException {
    return FindProductResponse.builder()
        .product(productService.loadProductDetails(id))
        .success(true)
        .build();
  }

  @GetMapping
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public ResponseEntity<List<Product>> getProductByPaging(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "20") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    List<Product> products = productService.loadAllProducts(pageNo, pageSize, sortBy);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }

  @PostMapping(
      value = "/get-all-v2",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public ResponseEntity<List<Product>> getAllProductByFilter(@RequestBody GetAllProductRequest request) {
    return new ResponseEntity<>(productService.loadAllProductByFilter(request), HttpStatus.OK);
  }
}
