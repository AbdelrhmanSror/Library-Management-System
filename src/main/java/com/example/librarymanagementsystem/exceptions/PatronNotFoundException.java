package com.example.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PatronNotFoundException extends ResponseStatusException {

    public PatronNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Patron not found");
    }
}
