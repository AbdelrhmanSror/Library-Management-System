package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class UserBadCredentialException extends CustomException {
    public UserBadCredentialException() {
        super("Bad credentials!");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public int getCustomCode() {
        return 458;
    }

}
