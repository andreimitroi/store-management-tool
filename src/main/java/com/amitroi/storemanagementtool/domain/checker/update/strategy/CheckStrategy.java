package com.amitroi.storemanagementtool.domain.checker.update.strategy;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;

public interface CheckStrategy {

  void check(ProductUpdate update);
}
