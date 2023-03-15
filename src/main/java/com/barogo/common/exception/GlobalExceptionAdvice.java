package com.barogo.common.exception;

import com.barogo.common.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
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
  protected String bindException(BindException e) {
    log("BindException", e);
    return e.getMessage();
  }

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  protected ResponseDTO<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//    log("MethodArgumentNotValidException", e);
//    return ResponseDTO.badRequest(getFieldErrors(e.getBindingResult()));
//  }
//
//  private List<CustomFieldError> getFieldErrors(BindingResult bindingResult) {
//    var errors = bindingResult.getFieldErrors();
//    if (CollectionUtils.isEmpty(errors)) {
//      return Collections.emptyList();
//    }
//    return errors.stream()
//        .map(CustomFieldError::of)
//        .toList();
//  }

  private void log(String handler, Throwable e) {
    log.error("[{}: {}]", handler, e.getClass().getSimpleName(), e);
  }

//
//  @Getter
//  @Builder
//  @ToString
//  public static class CustomFieldError {
//
//    private String field;
//    private String value;
//    private String reason;
//
//    public static CustomFieldError of(FieldError fieldError) {
//      return CustomFieldError.builder()
//          .reason(fieldError.getDefaultMessage())
//          .field(fieldError.getField())
//          .value((String) fieldError.getRejectedValue())
//          .build();
//    }
//  }

}