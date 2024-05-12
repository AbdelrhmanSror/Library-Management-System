package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException() {
        super("Invalid JWT!");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public int getCustomCode() {
        return 459;
    }
}