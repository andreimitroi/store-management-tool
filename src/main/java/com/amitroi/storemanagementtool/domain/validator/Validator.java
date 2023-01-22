package com.amitroi.storemanagementtool.domain.validator;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;

public abstract class Validator {

  private Validator next;

  public static Validator chain(Validator first, Validator... chain) {
    Validator head = first;
    for (Validator nextInChain : chain) {
      head.next = nextInChain;
      head = nextInChain;
    }
    return first;
  }

  public abstract boolean validate(ProductUpdate productUpdate);

  protected boolean checkNext(ProductUpdate productUpdate) {
    if (next == null) {
      return true;
    }
    return next.validate(productUpdate);
  }
}
