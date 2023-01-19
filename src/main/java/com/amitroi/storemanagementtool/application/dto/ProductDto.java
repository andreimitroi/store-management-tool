package com.amitroi.storemanagementtool.application.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDto {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}
