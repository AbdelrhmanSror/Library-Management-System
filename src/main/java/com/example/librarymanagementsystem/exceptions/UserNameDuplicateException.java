package com.example.librarymanagementsystem.exceptions;


import org.springframework.http.HttpStatus;

public class UserNameDuplicateException extends CustomException {
    public UserNameDuplicateException() {
        super("User name already in use");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public int getCustomCode() {
        return 457;
    }

}