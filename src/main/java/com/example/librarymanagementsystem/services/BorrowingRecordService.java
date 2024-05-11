package com.example.librarymanagementsystem.services;

public interface BorrowingRecordService {
    void borrowBook(Long bookId, Long patronId);

    void recordBookReturn(Long borrowingRecordId);

}
