package com.example.librarymanagementsystem.services;

public interface BorrowingService {
    void borrowBook(Long bookId, Long patronId);
}
