package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.entities.Book;
import com.example.librarymanagementsystem.entities.BorrowingRecord;
import com.example.librarymanagementsystem.entities.Patron;
import com.example.librarymanagementsystem.exceptions.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptions.BookNotFoundException;
import com.example.librarymanagementsystem.exceptions.BorrowingRecordNotFoundException;
import com.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import com.example.librarymanagementsystem.repos.BookRepository;
import com.example.librarymanagementsystem.repos.BorrowingRecordRepository;
import com.example.librarymanagementsystem.repos.PatronRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    @Override
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(PatronNotFoundException::new);

        // Check if the book is already borrowed
        if (bookIsBorrowed(book)) {
            throw new BookAlreadyBorrowedException();
        }
        // Convert LocalDate to Date
        Date borrowingDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Create a new borrowing record
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(borrowingDate)
                .build();

        borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    public void recordBookReturn(Long borrowingRecordId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(borrowingRecordId)
                .orElseThrow(BorrowingRecordNotFoundException::new);

        // Set return date to current date
        borrowingRecord.setReturnDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Update the borrowing record with return date
        borrowingRecordRepository.save(borrowingRecord);
    }
    private boolean bookIsBorrowed(Book book) {
        Optional<BorrowingRecord> borrowingRecord = borrowingRecordRepository.findByBookAndReturnDateIsNull(book);
        return borrowingRecord.isPresent();
    }
}
