package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyBorrowedException extends ResponseStatusException {

    public BookAlreadyBorrowedException() {
        super(HttpStatus.BAD_REQUEST, "Book already borrowed");
    }
}
