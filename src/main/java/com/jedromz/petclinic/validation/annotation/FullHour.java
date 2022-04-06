package com.jedromz.petclinic.validation.annotation;

import com.jedromz.petclinic.validation.implementation.FullHourValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FullHourValidator.class)
public @interface FullHour {

    String message() default "HOUR_NOT_FULL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
