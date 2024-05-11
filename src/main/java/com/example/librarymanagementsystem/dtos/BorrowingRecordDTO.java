package com.example.librarymanagementsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecordDTO {
    private Long bookId;
    private Long patronId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
