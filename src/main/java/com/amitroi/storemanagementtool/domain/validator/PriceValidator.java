package com.amitroi.storemanagementtool.domain.validator;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.PRICE_VALIDATION_MESSAGE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class PriceValidator extends Validator {

  @Override
  public boolean validate(ProductUpdate productUpdate) {
    if (nonNull(productUpdate.getPrice()) && ZERO.compareTo(productUpdate.getPrice()) > 0) {
      throw new CustomException(PRICE_VALIDATION_MESSAGE, ExceptionType.VALIDATION, "price",
          productUpdate.getPrice());
    }

    return checkNext(productUpdate);
  }
}
