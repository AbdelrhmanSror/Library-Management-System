package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.entities.Book;
import com.example.librarymanagementsystem.exceptions.BookNotFoundException;
import com.example.librarymanagementsystem.repos.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CacheConfig(cacheNames = "books")
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Cacheable(key = "'allBooks'")
    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Override
    public BookDTO getBookById(Long id) {
        Optional<BookDTO> optionalBookDTO = bookRepository.findById(id).map(this::convertToDTO);
        return optionalBookDTO.orElseThrow(BookNotFoundException::new);
    }

    @Override
    public Long addBook(BookDTO bookDTO) {
        Book savedBook = bookRepository.save(convertToEntity(bookDTO));
        return savedBook.getId();
    }


    @Override
    public void updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(bookDTO.getTitle());
            existingBook.setAuthor(bookDTO.getAuthor());
            existingBook.setPublicationYear(bookDTO.getPublicationYear());
            existingBook.setIsbn(bookDTO.getIsbn());
            bookRepository.save(existingBook);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException();
        }
    }

    private BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .isbn(book.getIsbn())
                .build();
    }

    private Book convertToEntity(BookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publicationYear(bookDTO.getPublicationYear())
                .isbn(bookDTO.getIsbn())
                .build();
    }
}
