package com.amitroi.storemanagementtool.domain.validator;

import static com.amitroi.storemanagementtool.domain.exception.util.ValidationMessages.NAME_VALIDATION_MESSAGE;
import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class NameValidator extends Validator {

  @Override
  public boolean validate(ProductUpdate productUpdate) {
    if (nonNull(productUpdate.getName()) && productUpdate.getName().isBlank()) {
      throw new CustomException(NAME_VALIDATION_MESSAGE, ExceptionType.VALIDATION, "name",
          productUpdate.getName());
    }

    return checkNext(productUpdate);
  }
}
