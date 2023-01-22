package com.amitroi.storemanagementtool.domain.service;

import static java.lang.String.format;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;
import com.amitroi.storemanagementtool.domain.mapper.ProductMapper;
import com.amitroi.storemanagementtool.domain.repository.ProductRepository;
import com.amitroi.storemanagementtool.domain.validator.NameValidator;
import com.amitroi.storemanagementtool.domain.validator.PriceValidator;
import com.amitroi.storemanagementtool.domain.validator.QuantityValidator;
import com.amitroi.storemanagementtool.domain.validator.Validator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

  private final ProductMapper productMapper;
  private static final String WRONG_UUID_MESSAGE = "UUID: %s";
  private final ProductRepository productRepository;

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public Product findProduct(UUID productId) {
    Optional<Product> byId = productRepository.findByUuid(productId);
    if (byId.isEmpty()) {
      throw new CustomException(ExceptionType.GENERAL, format(WRONG_UUID_MESSAGE, productId));
    }
    return byId.get();
  }

  public Product saveProduct(Product newProduct) {
    assignUuid(newProduct);
    return productRepository.save(newProduct);
  }

  private void assignUuid(Product newProduct) {
    newProduct.setUuid(UUID.randomUUID());
    log.info("Assigned UUID {} to to product {}", newProduct.getUuid(), newProduct);
  }

  public Product updateProduct(UUID productId, ProductUpdate productUpdate) {
    validateUpdate(productUpdate);
    Optional<Product> productByUuid = productRepository.findByUuid(productId);

    if (productByUuid.isEmpty()) {
      throw new CustomException(ExceptionType.GENERAL, format(WRONG_UUID_MESSAGE, productId));
    }
    log.info("Found product to update for UUID {}", productId);
    Product productToUpdate = productByUuid.get();
    productMapper.updateProduct(productUpdate, productToUpdate);
    return productRepository.save(productToUpdate);
  }

  private void validateUpdate(ProductUpdate productUpdate) {
    Validator updateValidator = Validator.chain(
        new NameValidator(),
        new PriceValidator(),
        new QuantityValidator()
    );

    updateValidator.validate(productUpdate);
  }

  public void deleteProduct(UUID productId) {
    Optional<Product> byId = productRepository.findByUuid(productId);
    if (byId.isEmpty()) {
      throw new CustomException(ExceptionType.GENERAL, format(WRONG_UUID_MESSAGE, productId));
    }
    productRepository.delete(byId.get());
  }

}
