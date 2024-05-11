package com.example.librarymanagementsystem.repos;

import com.example.librarymanagementsystem.entities.Book;
import com.example.librarymanagementsystem.entities.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndReturnDateIsNull(Book book);
}
