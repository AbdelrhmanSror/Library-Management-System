package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public abstract HttpStatus getStatus();
    public abstract int getCustomCode();

    public CustomException(String message) {
        super(message);
    }
}
