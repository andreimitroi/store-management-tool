package com.amitroi.storemanagementtool.application.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class NewProductRequest {

  @NotBlank(message = "Name can not be blank.")
  private String name;
  private String description;
  @PositiveOrZero(message = "Price can not be negative.")
  private BigDecimal price;
  @Positive(message = "Quantity must be above 0.")
  private Integer quantity;
}
