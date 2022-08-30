package com.sergey.accountservice.exception;

public class ProvidedPasswordException extends RuntimeException {
    public ProvidedPasswordException(String message) {
        super(message);
    }
}
