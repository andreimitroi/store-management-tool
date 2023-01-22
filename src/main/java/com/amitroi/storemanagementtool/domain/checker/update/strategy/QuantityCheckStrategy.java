package com.amitroi.storemanagementtool.domain.checker.update.strategy;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.QUANTITY_VALIDATION_MESSAGE;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class QuantityCheckStrategy implements CheckStrategy {

  @Override
  public void check(ProductUpdate update) {
    if (nonNull(update.getQuantity()) && update.getQuantity() < 0) {
      throw new CustomException(QUANTITY_VALIDATION_MESSAGE, ExceptionType.VALIDATION,
          "quantity",
          update.getQuantity());
    }
  }
}
