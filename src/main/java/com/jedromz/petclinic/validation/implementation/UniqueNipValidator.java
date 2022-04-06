package com.jedromz.petclinic.validation.implementation;
import com.jedromz.petclinic.service.VetService;
import com.jedromz.petclinic.validation.annotation.UniqueNip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
@RequiredArgsConstructor
public class UniqueNipValidator implements ConstraintValidator<UniqueNip, String> {

    private final VetService vetService;

    @Override
    public boolean isValid(String nip, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
