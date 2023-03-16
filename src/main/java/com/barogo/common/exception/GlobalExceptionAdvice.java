package com.barogo.common.exception;

import com.barogo.common.constant.ErrorMessage;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected String unknownException(Exception e) {
    log("Exception", e);
    return ErrorMessage.UNKNOWN;
  }

  @ExceptionHandler(APIException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected String apiException(APIException e) {
    log("APIException", e);
    return e.getMessage();
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected List<CustomFieldError> bindException(BindException e) {
    log("BindException", e);
    return getFieldErrors(e.getBindingResult());
  }

  private List<CustomFieldError> getFieldErrors(BindingResult bindingResult) {
    var errors = bindingResult.getFieldErrors();
    if (CollectionUtils.isEmpty(errors)) {
      return Collections.emptyList();
    }
    return errors.stream()
        .map(CustomFieldError::of)
        .toList();
  }

  private void log(String handler, Throwable e) {
    log.error("[{}: {}]", handler, e.getClass().getSimpleName(), e);
  }


  @Getter
  @Builder
  @ToString
  public static class CustomFieldError {

    private String field;
    private String value;
    private String message;

    public static CustomFieldError of(FieldError fieldError) {
      return CustomFieldError.builder()
          .message(fieldError.getDefaultMessage())
          .field(fieldError.getField())
          .value(String.valueOf(fieldError.getRejectedValue()))
          .build();
    }
  }

}