package com.example.librarymanagementsystem.services;

public interface BorrowingRecordService {
    Long borrowBook(Long bookId, Long patronId);

    void recordBookReturn(Long borrowingRecordId);

}
