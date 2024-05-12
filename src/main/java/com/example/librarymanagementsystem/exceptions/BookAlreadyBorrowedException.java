package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class BookAlreadyBorrowedException extends CustomException {

    public BookAlreadyBorrowedException() {
        super("Book already borrowed");
    }

    @Override
    public int getCustomCode() {
        return 452;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
