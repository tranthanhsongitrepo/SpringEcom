package com.example.springecom.user.exception;

public class UserExistsException extends UserPersistentException {
    public UserExistsException(String message, Exception exception) {
        super(message, exception);
    }
}
