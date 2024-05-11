package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BorrowingRecordNotFoundException extends ResponseStatusException {

    public BorrowingRecordNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Borrowing record not found");
    }
}