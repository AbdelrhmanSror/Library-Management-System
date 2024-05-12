package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.dtos.PatronDTO;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static com.example.librarymanagementsystem.TestSetupUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Specify order of test execution
class PatronControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String addPatronId;
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    @Order(1)
    void setUp() {
        log.info("Setting up test");
        AuthenticationRequestModel request = createAuthenticationRequest("name", "password");
        registerUser(restTemplate, request);
        String jwtToken = authenticateUser(restTemplate, request).getToken();
        this.headers.setBearerAuth(jwtToken);
    }

    @Test
    @Order(2)
    void testAddPatron() {
        log.info("Executing testAddPatron");
        PatronDTO patronDTO = new PatronDTO();
        HttpEntity<PatronDTO> requestEntity = new HttpEntity<>(patronDTO, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/patrons", requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        addPatronId = response.getBody();
        log.info("Patron added with ID: {}", addPatronId);
    }

    @Test
    @Order(3)
    void testUpdatePatron() {
        log.info("Executing testUpdatePatron");
        PatronDTO patronDTO = new PatronDTO();
        HttpEntity<PatronDTO> requestEntity = new HttpEntity<>(patronDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/patrons/{id}", HttpMethod.PUT, requestEntity, Void.class, addPatronId);
        log.info("response returned " + response +addPatronId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void testDeletePatron() {
        log.info("Executing testDeletePatron");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/patrons/{id}", HttpMethod.DELETE, requestEntity, Void.class, addPatronId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
