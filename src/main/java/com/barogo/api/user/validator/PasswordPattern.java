package com.barogo.api.user.validator;

import com.barogo.common.constant.ErrorMessage;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordPatterValidator.class)
public @interface PasswordPattern {

  String message() default ErrorMessage.INVALID_PASSWORD;

  Class[] groups() default {};

  Class[] payload() default {};
}