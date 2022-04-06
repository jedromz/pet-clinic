package com.jedromz.petclinic.error;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        return new ResponseEntity(exc.getFieldErrors().stream().map(fe -> new ValidationErrorDto(fe.getDefaultMessage(), fe.getField())).collect(toList()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException exc) {
        return new ResponseEntity(new NotFoundDto(exc.getName(), exc.getKey()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleConflictException.class)
    public ResponseEntity handleEntityNotFoundException(ScheduleConflictException exc) {
        return new ResponseEntity(new ValidationErrorDto("DATE_CONFLICT", "VET_VISIT_DATE"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException exc) {
        //String constraintName = exc.getConstraintName().substring(1, exc.getConstraintName().indexOf(" "));
        String message = null;
        String field = null;
        //if ("UC_STUDENT_EMAIL".equals(constraintName)) {
        message = "EMIAL_NOT_UNIQUE";
        field = "email";
        //}
        //TODO: jakis fajny mechnizm konfiguracyjny dla pozyskania informacji o message i polu ktorego dotyczy constraint name.
        return new ResponseEntity(new ValidationErrorDto(message, field), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(org.hibernate.exception.ConstraintViolationException exc) {
        String constraintName = exc.getConstraintName().substring(1, exc.getConstraintName().indexOf(" "));
        String message = null;
        String field = null;
        if ("UC_STUDENT_EMAIL".equals(constraintName)) {
            message = "EMIAL_NOT_UNIQUE";
            field = "email";
        }
        //TODO: jakis fajny mechnizm konfiguracyjny dla pozyskania informacji o message i polu ktorego dotyczy constraint name.
        return new ResponseEntity(new ValidationErrorDto(message, field), HttpStatus.BAD_REQUEST);
    }


    @Value
    class ValidationErrorDto {
        private String message;
        private String field;
    }

    @Value
    class NotFoundDto {
        private String entityName;
        private String entityKey;
    }
}
