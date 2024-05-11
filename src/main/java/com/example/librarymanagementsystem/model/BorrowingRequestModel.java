package com.example.librarymanagementsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BorrowingRequestModel {
    private Long bookId;
    private Long patronId;
}
