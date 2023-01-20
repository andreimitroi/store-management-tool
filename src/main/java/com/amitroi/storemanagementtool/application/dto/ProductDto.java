package com.amitroi.storemanagementtool.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductDto {

  private UUID productId;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}
