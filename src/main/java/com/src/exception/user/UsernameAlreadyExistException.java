package com.src.exception.user;

public class UsernameAlreadyExistException extends Exception {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
