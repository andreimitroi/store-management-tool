package com.amitroi.storemanagementtool.application.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductUpdate {

  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}