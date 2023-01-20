package com.amitroi.storemanagementtool.domain.mapper;

import com.amitroi.storemanagementtool.application.dto.ProductDto;
import com.amitroi.storemanagementtool.application.model.NewProductRequest;
import com.amitroi.storemanagementtool.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  Product productDtoToProduct(ProductDto productDto);
  ProductDto productToProductDto(Product product);

  Product newProductRequestToProduct(NewProductRequest newProductRequest);
}
