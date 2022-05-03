package com.jedromz.petclinic.error.constraint;

public interface ConstraintErrorHandler {
    String constraintName();

    String message();

    String field();
}
