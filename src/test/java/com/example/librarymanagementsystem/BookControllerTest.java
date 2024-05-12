package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static com.example.librarymanagementsystem.TestSetupUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String addBookId;
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        log.info("Setting up test");
        AuthenticationRequestModel request = createAuthenticationRequest("name", "password");
        registerUser(restTemplate, request);
        String jwtToken = authenticateUser(restTemplate, request).getToken();
        this.headers.setBearerAuth(jwtToken);
    }

    @Test
    void testAddBook() {
        log.info("Executing testAddBook");
        BookDTO bookDTO = new BookDTO();
        HttpEntity<BookDTO> requestEntity = new HttpEntity<>(bookDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/books", requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        addBookId = response.getBody();
        log.info("Book added with ID: {}", addBookId);
    }

    @Test
    void testUpdateBook() {
        log.info("Executing testUpdateBook");
        BookDTO bookDTO = new BookDTO();
        HttpEntity<BookDTO> requestEntity = new HttpEntity<>(bookDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/books/{id}", HttpMethod.PUT, requestEntity, Void.class, addBookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteBook() {
        log.info("Executing testDeleteBook");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/books/{id}", HttpMethod.DELETE, requestEntity, Void.class, addBookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
