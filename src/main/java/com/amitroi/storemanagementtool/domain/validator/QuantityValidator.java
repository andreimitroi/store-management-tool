package com.amitroi.storemanagementtool.domain.validator;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.QUANTITY_VALIDATION_MESSAGE;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class QuantityValidator extends Validator {

  @Override
  public boolean validate(ProductUpdate productUpdate) {
    if (nonNull(productUpdate.getQuantity()) && productUpdate.getQuantity() < 0) {
      throw new CustomException(QUANTITY_VALIDATION_MESSAGE, ExceptionType.VALIDATION,
          "quantity",
          productUpdate.getQuantity());
    }

    return checkNext(productUpdate);
  }
}
