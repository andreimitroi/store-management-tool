package com.amitroi.storemanagementtool.domain.checker.update.strategy;

import static java.util.Objects.nonNull;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;

public class NameCheckStrategy implements CheckStrategy {

  @Override
  public void check(ProductUpdate update) {
    if (nonNull(update.getName()) && update.getName().isBlank()) {
      throw new CustomException("Name can not be blank.", ExceptionType.VALIDATION, "name",
          update.getName());
    }
  }
}
