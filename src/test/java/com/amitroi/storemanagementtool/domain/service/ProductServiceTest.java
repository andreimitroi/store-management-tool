package com.amitroi.storemanagementtool.domain.service;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.entity.Product;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.mapper.ProductMapper;
import com.amitroi.storemanagementtool.domain.mapper.ProductMapperImpl;
import com.amitroi.storemanagementtool.domain.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  ProductRepository productRepository;
  @Spy
  ProductMapper productMapper = new ProductMapperImpl();
  @InjectMocks
  ProductService productService;

  @Test
  @DisplayName("Test findAllProducts Success")
  void findAllProductsTest() {
    List<Product> expected = getMockProducts();
    when(productRepository.findAll()).thenReturn(expected);

    List<Product> actual = productService.findAllProducts();
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test findAllProducts Empty")
  void findAllProductsEmptyTest() {
    List<Product> actual = productService.findAllProducts();
    assertTrue(actual.isEmpty());
  }

  @Test
  @DisplayName("Test findProduct Success")
  void findProductTest() {
    Product expected = getMockProduct(1L, "Product_A");
    when(productRepository.findByUuid(expected.getUuid())).thenReturn(of(expected));

    Product actual = productService.findProduct(expected.getUuid());
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Test findProduct Exception")
  void findProductExceptionTest() {
    UUID uuid = UUID.randomUUID();
    assertThrows(CustomException.class, () -> productService.findProduct(uuid));
  }

  @Test
  @DisplayName("Test saveProduct Success")
  void saveProductTest() {
    Product toSave = new Product();
    toSave.setId(1L);
    toSave.setName("Product_A");

    when(productRepository.save(toSave)).thenAnswer(returnsFirstArg());

    Product saved = productService.saveProduct(toSave);

    verify(productRepository).save(any());
    assertNotNull(saved.getUuid());
    assertEquals(toSave.getId(), saved.getId());
    assertEquals(toSave.getName(), saved.getName());
  }

  @Test
  @DisplayName("Test updateProduct Success")
  void updateProductTest() {
    Product toUpdate = getMockProduct(1L, "Product_A");
    toUpdate.setPrice(BigDecimal.ONE);

    UUID uuid = toUpdate.getUuid();

    ProductUpdate productUpdate = new ProductUpdate();
    productUpdate.setName("Product_A");
    productUpdate.setPrice(BigDecimal.TEN);

    when(productRepository.findByUuid(uuid)).thenReturn(of(toUpdate));
    when(productRepository.save(any())).thenAnswer(returnsFirstArg());

    Product updated = productService.updateProduct(uuid, productUpdate);

    assertEquals(BigDecimal.TEN, updated.getPrice());
  }

  @Test
  @DisplayName("Test updateProduct Product Not Found")
  void updateProductNotFoundTest() {
    UUID uuid = UUID.randomUUID();

    ProductUpdate productUpdate = new ProductUpdate();
    productUpdate.setName("Product_A");
    productUpdate.setPrice(BigDecimal.TEN);

    when(productRepository.findByUuid(uuid)).thenReturn(empty());

    assertThrows(CustomException.class, () -> productService.updateProduct(uuid, productUpdate));
  }

  @Test
  @DisplayName("Test updateProduct Validation Exception")
  void updateProductValidationExceptionTest() {
    UUID uuid = UUID.randomUUID();
    ProductUpdate productUpdate = new ProductUpdate();
    productUpdate.setName("");

    assertThrows(CustomException.class, () -> productService.updateProduct(uuid, productUpdate));
  }

  @Test
  @DisplayName("Test deleteProduct Success")
  void deleteProductTest() {
    Product toDelete = getMockProduct(1L, "Product_A");
    when(productRepository.findByUuid(toDelete.getUuid())).thenReturn(of(toDelete));

    productService.deleteProduct(toDelete.getUuid());

    verify(productRepository).findByUuid(toDelete.getUuid());
    verify(productRepository).delete(toDelete);
  }

  @Test
  @DisplayName("Test deleteProduct Exception")
  void deleteProductExceptionTest() {
    UUID uuid = UUID.randomUUID();
    when(productRepository.findByUuid(uuid)).thenReturn(empty());

    assertThrows(CustomException.class, () -> productService.deleteProduct(uuid));
  }

  List<Product> getMockProducts() {
    return List.of(getMockProduct(1L, "Product_A"), getMockProduct(2L, "Product_B"),
        getMockProduct(3L, "Product_C"));
  }

  Product getMockProduct(Long id, String name) {
    Product mockedProduct = new Product();
    mockedProduct.setId(id);
    mockedProduct.setUuid(UUID.randomUUID());
    mockedProduct.setName(name);
    return mockedProduct;
  }


}