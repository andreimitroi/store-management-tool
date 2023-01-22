package com.amitroi.storemanagementtool.domain.exception.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {

  public static final String NAME_VALIDATION_MESSAGE = "Name can not be blank";
  public static final String PRICE_VALIDATION_MESSAGE = "Price can not be negative";
  public static final String QUANTITY_VALIDATION_MESSAGE = "Quantity must be above 0";

}
