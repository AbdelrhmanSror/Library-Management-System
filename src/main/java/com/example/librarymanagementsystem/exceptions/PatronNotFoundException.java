package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class PatronNotFoundException extends CustomException {


    public PatronNotFoundException() {
        super("Patron not found");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public int getCustomCode() {
        return 455;
    }
}
