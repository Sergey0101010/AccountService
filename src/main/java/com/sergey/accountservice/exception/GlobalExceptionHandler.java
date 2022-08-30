package com.sergey.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<CustomErrorMessage> handleRegistrationException(
            UserRegistrationException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessage> handleNotValidRegisterException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ) {

        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Password length must be 12 chars minimum!",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProvidedPasswordException.class)
    public ResponseEntity<CustomErrorMessage> providedPasswordException(
            ProvidedPasswordException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeePaymentException.class)
    public ResponseEntity<CustomErrorMessage> handleEmployeePaymentException(
            EmployeePaymentException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CustomErrorMessage> handleDateParseException(
            DateTimeParseException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteUserException.class)
    public ResponseEntity<CustomErrorMessage> handleUserDeleteException(
            DeleteUserException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteAdminException.class)
    public ResponseEntity<CustomErrorMessage> handleDeleteAdminException(
            DeleteAdminException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateUserRoleException.class)
    public ResponseEntity<CustomErrorMessage> handleUpdateUserException(
            UpdateUserRoleException exception,
            WebRequest request
    ) {
        CustomErrorMessage responseBody = new CustomErrorMessage(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
