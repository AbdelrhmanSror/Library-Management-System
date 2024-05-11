package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.entities.Book;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long id);

    void addBook(BookDTO book);

    void updateBook(Long id, BookDTO book);

    void deleteBook(Long id);
}
