package com.amitroi.storemanagementtool.domain.checker.update.strategy;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.PRICE_VALIDATION_MESSAGE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class PriceCheckStrategy implements CheckStrategy {

  @Override
  public void check(ProductUpdate update) {
    if (nonNull(update.getPrice()) && ZERO.compareTo(update.getPrice()) > 0) {
      throw new CustomException(PRICE_VALIDATION_MESSAGE, ExceptionType.VALIDATION, "price",
          update.getPrice());
    }
  }
}
