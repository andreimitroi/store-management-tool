package com.amitroi.storemanagementtool.domain.mapper;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.amitroi.storemanagementtool.application.dto.ProductDto;
import com.amitroi.storemanagementtool.application.model.NewProductRequest;
import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = IGNORE)
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", source = "productId")
  Product productDtoToProduct(ProductDto productDto);

  @Mapping(target = "productId", source = "uuid")
  ProductDto productToProductDto(Product product);

  List<ProductDto> productToProductDto(List<Product> products);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  Product newProductRequestToProduct(NewProductRequest newProductRequest);

  void updateProduct(ProductUpdate productUpdate, @MappingTarget Product product);

}
