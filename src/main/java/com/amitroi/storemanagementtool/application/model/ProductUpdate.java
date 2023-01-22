package com.amitroi.storemanagementtool.application.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdate {

  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}