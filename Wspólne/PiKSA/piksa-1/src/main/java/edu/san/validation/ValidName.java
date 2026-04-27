// © 2026 Konrad Grzanek <kongra@gmail.com>
package edu.san.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD, ElementType.RECORD_COMPONENT,
    ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {

  String message() default "name must start with an uppercase letter and must not end with an uppercase letter";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
