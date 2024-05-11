package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Book not found");
    }
}