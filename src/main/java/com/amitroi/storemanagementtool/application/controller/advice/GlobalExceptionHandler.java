package com.amitroi.storemanagementtool.application.controller.advice;

import static java.util.stream.Collectors.toMap;

import com.amitroi.storemanagementtool.domain.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, Object> handleValidationException(
      MethodArgumentNotValidException argumentNotValidException) {
    Map<String, Object> fieldErrors = argumentNotValidException.getBindingResult().getFieldErrors()
        .stream().collect(
            toMap(FieldError::getField, FieldError::getDefaultMessage));
    log.error("Validation failed for {} due to the following violations {}.",
        argumentNotValidException.getTarget(), fieldErrors);
    return fieldErrors;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(CustomException.class)
  public String handleBusinessException(CustomException customException,
      HttpServletRequest request) {
    String userMessage = messageSource.getMessage(customException.getType().name(),
        customException.getParams(), request.getLocale());
    log.error("Exception occured: " + userMessage, customException);
    return userMessage;
  }
}
