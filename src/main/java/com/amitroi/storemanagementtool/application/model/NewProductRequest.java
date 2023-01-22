package com.amitroi.storemanagementtool.application.model;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.NAME_VALIDATION_MESSAGE;
import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.PRICE_VALIDATION_MESSAGE;
import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.QUANTITY_VALIDATION_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class NewProductRequest {

  @NotBlank(message = NAME_VALIDATION_MESSAGE)
  private String name;
  private String description;
  @PositiveOrZero(message = PRICE_VALIDATION_MESSAGE)
  private BigDecimal price;
  @Positive(message = QUANTITY_VALIDATION_MESSAGE)
  private Integer quantity;
}
