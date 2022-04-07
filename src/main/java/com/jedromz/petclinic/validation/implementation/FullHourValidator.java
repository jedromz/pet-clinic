package com.jedromz.petclinic.validation.implementation;

import com.jedromz.petclinic.validation.annotation.FullHour;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FullHourValidator implements ConstraintValidator<FullHour, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return false;
        }
        return localDateTime.getMinute() == 0;
    }
}
