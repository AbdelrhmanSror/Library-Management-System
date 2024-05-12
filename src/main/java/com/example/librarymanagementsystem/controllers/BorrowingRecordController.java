package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.model.BorrowingRequestModel;
import com.example.librarymanagementsystem.model.ReturnRequestModel;
import com.example.librarymanagementsystem.services.BorrowingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/borrowing")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @Operation(summary = "Borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book borrowed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping("/borrow")
    public ResponseEntity<Long> borrowBook(@RequestBody BorrowingRequestModel request) {
        Long recordId = borrowingRecordService.borrowBook(request.getBookId(), request.getPatronId());
        return ResponseEntity.status(HttpStatus.CREATED).body(recordId);
    }

    @Operation(summary = "Record the return of a borrowed book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book return recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Borrowing record not found")
    })
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody ReturnRequestModel request) {
        borrowingRecordService.recordBookReturn(request.getBorrowingRecordId());
        return ResponseEntity.ok("Book return recorded successfully");
    }


}
