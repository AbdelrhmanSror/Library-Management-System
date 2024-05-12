package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.dtos.PatronDTO;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.BorrowingRequestModel;
import com.example.librarymanagementsystem.model.ReturnRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.librarymanagementsystem.TestSetupUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Specify order of test execution
class BorrowingRecordControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final HttpHeaders headers = new HttpHeaders();
    private static String addBookId;
    private static String addPatronId;
    private static String addBorrowingRecordId;


    @BeforeEach
    @Order(1)
    void setUp() {
        log.info("Setting up test");
        AuthenticationRequestModel request = createAuthenticationRequest("name", "password");
        registerUser(restTemplate, request);
        String jwtToken = authenticateUser(restTemplate, request).getToken();
        this.headers.setBearerAuth(jwtToken);
        BookDTO bookDTO = new BookDTO();
        PatronDTO patronDTO = new PatronDTO();
        HttpEntity<BookDTO> bookRequestEntity = new HttpEntity<>(bookDTO, headers);
        HttpEntity<PatronDTO> patronRequestEntity = new HttpEntity<>(patronDTO, headers);
        addBookId = createBook(restTemplate, bookRequestEntity);
        addPatronId = createPatron(restTemplate, patronRequestEntity);

    }

    @Order(1)
    @Test
    void testBorrow() {
        BorrowingRequestModel requestModel = BorrowingRequestModel.builder().bookId(Long.valueOf(addBookId)).patronId(Long.valueOf(addPatronId)).build();
        HttpEntity<BorrowingRequestModel> requestEntity = new HttpEntity<>(requestModel, headers);

        // When
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/borrowing/borrow", requestEntity, String.class);
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        addBorrowingRecordId = response.getBody();
    }

    @Order(2)
    @Test
    void testReturn() {
        log.info( addBorrowingRecordId);
        ReturnRequestModel requestModel = ReturnRequestModel.builder().borrowingRecordId(Long.valueOf(addBorrowingRecordId)).build();
        HttpEntity<ReturnRequestModel> requestEntity = new HttpEntity<>(requestModel, headers);

        // When
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/borrowing/return", requestEntity, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
