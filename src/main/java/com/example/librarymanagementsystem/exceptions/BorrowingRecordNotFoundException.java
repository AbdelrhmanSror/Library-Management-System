package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class BorrowingRecordNotFoundException extends CustomException {

    public BorrowingRecordNotFoundException() {
        super("Borrowing record not found");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public int getCustomCode() {
        return 454;
    }
}