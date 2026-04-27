// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank())
      return false;
    return Character.isUpperCase(value.charAt(0))
        && !Character.isUpperCase(value.charAt(value.length() - 1));
  }
}
