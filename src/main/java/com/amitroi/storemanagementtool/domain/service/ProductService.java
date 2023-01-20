package com.amitroi.storemanagementtool.domain.service;

import static java.util.Objects.isNull;

import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public Product findProduct(Long productId) {
    Optional<Product> byId = productRepository.findById(productId);
    if (byId.isEmpty()) {
      throw new RuntimeException("Product does not exist.");
    }
    return byId.get();
  }

  public Product saveProduct(Product newProduct) {
    return productRepository.save(newProduct);
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

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

}
