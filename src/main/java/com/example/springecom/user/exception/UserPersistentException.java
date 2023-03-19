package com.example.springecom.user.exception;

import java.sql.SQLException;

public class UserPersistentException extends RuntimeException {


    public UserPersistentException(String message, Exception exception) {
        super(message, exception);
    }
}
