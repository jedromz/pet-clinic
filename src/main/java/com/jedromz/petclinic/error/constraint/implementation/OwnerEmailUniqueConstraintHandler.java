package com.jedromz.petclinic.error.constraint.implementation;

import com.jedromz.petclinic.error.constraint.ConstraintErrorHandler;

public class OwnerEmailUniqueConstraintHandler implements ConstraintErrorHandler {
    @Override
    public String constraintName() {
        return "UC_OWNER_EMAIL";
    }

    @Override
    public String message() {
        return "EMAIL_NOT_UNIQUE";
    }

    @Override
    public String field() {
        return "owner_email";
    }
}
