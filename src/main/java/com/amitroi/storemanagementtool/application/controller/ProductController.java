package com.amitroi.storemanagementtool.application.controller;

import com.amitroi.storemanagementtool.application.dto.ProductDto;
import com.amitroi.storemanagementtool.application.model.NewProductRequest;
import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.mapper.ProductMapper;
import com.amitroi.storemanagementtool.domain.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  public static final String RECEIVED_REQUEST_MESSAGE = "Handling {} request for product {}";
  private final ProductService productService;
  private final ProductMapper productMapper;

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    log.info("Handling GET all products request...");
    return ResponseEntity.ok(productMapper.productToProductDto(productService.findAllProducts()));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductDto> getProduct(
      @PathVariable("productId") @org.hibernate.validator.constraints.UUID UUID uuid,
      HttpServletRequest request) {
    log.info(RECEIVED_REQUEST_MESSAGE, request.getMethod(), uuid);
    return ResponseEntity.ok(productMapper.productToProductDto(productService.findProduct(uuid)));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteProduct(
      @PathVariable("productId") @org.hibernate.validator.constraints.UUID UUID uuid,
      HttpServletRequest request) {
    log.info(RECEIVED_REQUEST_MESSAGE, request.getMethod(), uuid);
    productService.deleteProduct(uuid);
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<String> addProduct(
      @Valid @RequestBody NewProductRequest newProductRequest, HttpServletRequest request) {
    log.info(RECEIVED_REQUEST_MESSAGE, request.getMethod(), newProductRequest);
    Product toBeAdded = productMapper.newProductRequestToProduct(newProductRequest);
    Product addedProduct = productService.saveProduct(toBeAdded);
    URI location = UriComponentsBuilder.fromPath("/api/v1/products/{productId}")
        .buildAndExpand(addedProduct.getUuid().toString()).toUri();
    return ResponseEntity.created(location).build();
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable("productId") @org.hibernate.validator.constraints.UUID UUID productId,
      @NotNull @RequestBody ProductUpdate productUpdate, HttpServletRequest request) {
    log.info(RECEIVED_REQUEST_MESSAGE, request.getMethod(), productUpdate); //log not too accurate
    Product product = productService.updateProduct(productId, productUpdate);
    return ResponseEntity.ok(productMapper.productToProductDto(product));
  }

}
