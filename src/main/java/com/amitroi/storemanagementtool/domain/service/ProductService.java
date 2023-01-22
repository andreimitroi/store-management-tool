package com.amitroi.storemanagementtool.domain.service;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.checker.update.UpdateChecker;
import com.amitroi.storemanagementtool.domain.checker.update.strategy.NameCheckStrategy;
import com.amitroi.storemanagementtool.domain.checker.update.strategy.PriceCheckStrategy;
import com.amitroi.storemanagementtool.domain.checker.update.strategy.QuantityCheckStrategy;
import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;
import com.amitroi.storemanagementtool.domain.mapper.ProductMapper;
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
    UUID uuid = UUID.randomUUID();
    newProduct.setUuid(uuid);
  }

  public Product updateProductPrice(UUID productId, ProductUpdate productUpdate) {
    checkUpdateValid(productUpdate);
    Optional<Product> productByUuid = productRepository.findByUuid(productId);

    if (productByUuid.isEmpty()) {
      throw new CustomException(ExceptionType.GENERAL, format(WRONG_UUID_MESSAGE, productId));
    }
    Product productToUpdate = productByUuid.get();
    productMapper.updateProduct(productUpdate, productToUpdate);
    return productRepository.save(productToUpdate);
  }

  private void checkUpdateValid(ProductUpdate productUpdate) {
    if (nonNull(productUpdate.getName())) {
      UpdateChecker.builder().checkStrategy(new NameCheckStrategy()).build()
          .checkUpdate(productUpdate);
    }
    if (nonNull(productUpdate.getPrice())) {
      UpdateChecker.builder().checkStrategy(new PriceCheckStrategy()).build()
          .checkUpdate(productUpdate);
    }
    if (nonNull(productUpdate.getQuantity())) {
      UpdateChecker.builder().checkStrategy(new QuantityCheckStrategy()).build()
          .checkUpdate(productUpdate);
    }
  }

  public void deleteProduct(UUID productId) {
    Optional<Product> byId = productRepository.findByUuid(productId);
    if (byId.isEmpty()) {
      throw new CustomException(ExceptionType.GENERAL, format(WRONG_UUID_MESSAGE, productId));
    }
    productRepository.delete(byId.get());
  }

}
