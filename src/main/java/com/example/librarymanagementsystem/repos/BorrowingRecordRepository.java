package com.example.librarymanagementsystem.repos;

import com.example.librarymanagementsystem.entities.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
}
