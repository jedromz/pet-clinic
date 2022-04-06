package com.jedromz.petclinic.validation.implementation;

import com.jedromz.petclinic.service.PetService;
import com.jedromz.petclinic.validation.annotation.UniqueEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final PetService petService;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !petService.existsByEmail(email);
    }
}
