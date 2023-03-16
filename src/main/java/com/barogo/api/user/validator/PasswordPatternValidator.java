package com.barogo.api.user.validator;

import com.barogo.api.user.code.PasswordPatternCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordPatterValidator implements ConstraintValidator<PasswordPattern, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    var count = Arrays.stream(PasswordPatternCode.values())
        .mapToInt(code -> code.matched(value))
        .sum();

    return count >= 3 && value.length() >= 12;
  }

}
