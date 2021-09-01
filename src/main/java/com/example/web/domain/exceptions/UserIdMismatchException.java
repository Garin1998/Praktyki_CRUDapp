package com.example.web.domain.exceptions;

public class UserIdMismatchException extends RuntimeException {
    public UserIdMismatchException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Provided UUID doesn't match with existed user";
    }
}
