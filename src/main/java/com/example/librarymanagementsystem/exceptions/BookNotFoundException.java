package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends CustomException {

    public BookNotFoundException() {
        super("Book not found");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public int getCustomCode() {
        return 453;
    }
}