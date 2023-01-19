package com.amitroi.storemanagementtool.application.controller;

import com.amitroi.storemanagementtool.application.dto.ProductDto;
import com.amitroi.storemanagementtool.application.model.NewProductRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  @GetMapping
  public ResponseEntity<List<String>> getAllProducts() {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{productId}")
  public ResponseEntity<String> getProduct(@PathVariable("productId") Long id) {
    return ResponseEntity.ok("Product id: " + id);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long id) {
    return ResponseEntity.ok("Deleted product with id: " + id);
  }

  @PostMapping
  public ResponseEntity<String> addProduct(@RequestBody NewProductRequest newProductRequest) {
    return ResponseEntity.ok("Added product: " + newProductRequest);
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<String> updateProduct(@PathVariable("productId") Long id, @RequestBody ProductDto productDto) {
    return ResponseEntity.ok("Product with id: " + id + " updated: " + productDto);
  }
}
