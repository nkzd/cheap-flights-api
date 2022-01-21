package io.vukotic.flightadvisor.error.handler;

import io.vukotic.flightadvisor.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getFieldViolations().add(
                    new FieldViolation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onConstraintViolation(
            ConstraintViolationException e) {
        var error = new ValidationErrorResponse();
        for (var fieldError : e.getConstraintViolations()) {
            error.getFieldViolations().add(
                    new FieldViolation(fieldError.getPropertyPath().toString(), fieldError.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    SingleViolation onUserNotFoundException(
            UserNotFoundException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(CityAndCountryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    SingleViolation onCityAndCountryAlreadyExist(
            CityAndCountryAlreadyExistException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    SingleViolation onCityNotFound(
            CityNotFoundException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    SingleViolation onCommentNotFound(
            CommentNotFoundException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedToHandleCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    SingleViolation onUserNotAuthorizedToHandleComment(
            UserNotAuthorizedToHandleCommentException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    SingleViolation onUsernameAlreadyExists(
            UsernameAlreadyExistsException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(CorruptedFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    SingleViolation onCorruptedFile(
            CorruptedFileException e) {
        return new SingleViolation(e.getMessage());
    }

    @ExceptionHandler(NoFlightPathException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    SingleViolation onNoFlightPathException(
            NoFlightPathException e) {
        return new SingleViolation(e.getMessage());
    }
}
