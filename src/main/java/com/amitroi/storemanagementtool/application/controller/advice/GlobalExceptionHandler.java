package com.amitroi.storemanagementtool.application.controller.advice;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.amitroi.storemanagementtool.domain.exception.CustomException;
import com.amitroi.storemanagementtool.domain.exception.CustomException.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private static final String EXCEPTION_OCCURED = "Exception occured: ";
  private final MessageSource messageSource;

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, Object> handleValidationException(
      MethodArgumentNotValidException argumentNotValidException) {
    Map<String, Object> fieldErrors = argumentNotValidException.getBindingResult().getFieldErrors()
        .stream().collect(
            toMap(FieldError::getField, FieldError::getDefaultMessage));
    log.error("Validation failed for {} due to the following violations {}. {}",
        argumentNotValidException.getTarget(), fieldErrors, argumentNotValidException);
    return fieldErrors;
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public String handleRequestArgumentsNotValidException(
      MethodArgumentTypeMismatchException exception,
      HttpServletRequest request) {
    log.error(EXCEPTION_OCCURED + exception);
    return messageSource.getMessage(ExceptionType.REQUEST_ISSUE.name(),
        singletonList(exception.getCause().getMessage()).toArray(), request.getLocale());
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public String handleRequestNotReadableException(HttpMessageNotReadableException exception,
      HttpServletRequest request) {
    log.error(EXCEPTION_OCCURED + exception);
    return messageSource.getMessage(ExceptionType.REQUEST_ISSUE.name(), null, request.getLocale());
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(CustomException.class)
  public String handleBusinessException(CustomException customException,
      HttpServletRequest request) {
    String userMessage = messageSource.getMessage(customException.getType().name(),
        customException.getParams(), request.getLocale());
    log.error(EXCEPTION_OCCURED + userMessage, customException);
    return userMessage;
  }
}
