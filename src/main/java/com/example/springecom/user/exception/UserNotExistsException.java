package com.example.springecom.user.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String formatted, Exception dataIntegrityViolationException) {
        super(formatted, dataIntegrityViolationException);
    }
}
