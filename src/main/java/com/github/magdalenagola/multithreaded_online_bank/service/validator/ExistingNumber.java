package com.github.magdalenagola.multithreaded_online_bank.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AccountValidator.class)
@Documented
public @interface ExistingNumber {

    String message() default "{AccountNumber.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
