package com.amitroi.storemanagementtool.domain.service;

import static java.lang.String.format;
import static java.util.Objects.isNull;

import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;
import com.amitroi.storemanagementtool.domain.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private static final String WRONG_UUID_MESSAGE = "UUID: %s";
  private final ProductRepository productRepository;

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public Product findProduct(UUID productId) {
    Optional<Product> byId = productRepository.findByUuid(productId);
    if (byId.isEmpty()) {
      throw new CustomException(ExceptionType.JPA_STUFF, format(WRONG_UUID_MESSAGE, productId));
    }
    return byId.get();
  }

  public Product saveProduct(Product newProduct) {
    assignUuid(newProduct);
    return productRepository.save(newProduct);
  }

  private void assignUuid(Product newProduct) {
    UUID uuid = UUID.randomUUID();
    newProduct.setUuid(uuid);
  }

  public Product updateProduct(Long id, Product updatedProduct) {
    Optional<Product> productById = productRepository.findById(id);

    if (productById.isEmpty()) {
      //TODO create specific exception and throw it instead of a generic one
      throw new RuntimeException("Product to be updated is not existing.");
    }
    if (isNull(updatedProduct.getId())) {
      updatedProduct.setId(id);
    }
    return productRepository.save(updatedProduct);
  }

  public void deleteProduct(UUID productId) {
    Optional<Product> byId = productRepository.findByUuid(productId);
    if (byId.isEmpty()) {
      throw new CustomException(ExceptionType.JPA_STUFF, format(WRONG_UUID_MESSAGE, productId));
    }
    productRepository.delete(byId.get());
  }

}
