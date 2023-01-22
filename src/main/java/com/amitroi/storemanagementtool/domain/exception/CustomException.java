package com.amitroi.storemanagementtool.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

  public enum ExceptionType {
    GENERAL,
    OTHER_REASON,
    JPA_STUFF,
    VALIDATION
  }

  private final ExceptionType type;
  private final Object[] params;

  public CustomException(ExceptionType type, Object... params) {
    this.type = type;
    this.params = params;
  }

  public CustomException(String message, ExceptionType type, Object... params) {
    super(message);
    this.type = type;
    this.params = params;
  }

  public CustomException(String message, Throwable cause, ExceptionType type, Object... params) {
    super(message, cause);
    this.type = type;
    this.params = params;
  }

  public CustomException(Throwable cause, ExceptionType type, Object... params) {
    super(cause);
    this.type = type;
    this.params = params;
  }
}
