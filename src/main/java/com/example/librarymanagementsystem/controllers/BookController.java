package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Add a new book")
    @PostMapping
    public ResponseEntity<Long> addBook(@RequestBody BookDTO bookDTO) {
        Long bookId = bookService.addBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId);
    }


    @Operation(summary = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
