package com.amitroi.storemanagementtool.domain.checker.update;

import com.amitroi.storemanagementtool.application.model.ProductUpdate;
import com.amitroi.storemanagementtool.domain.checker.update.strategy.CheckStrategy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateChecker {

  private CheckStrategy checkStrategy;

  public void checkUpdate(ProductUpdate update) {
    checkStrategy.check(update);
  }
}
