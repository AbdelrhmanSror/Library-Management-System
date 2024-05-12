package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.AuthenticationResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.librarymanagementsystem.TestSetupUtil.createAuthenticationRequest;
import static com.example.librarymanagementsystem.TestSetupUtil.registerUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Specify order of test execution
class UserAuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Order(1)
    @BeforeEach
    void setUp() {
        AuthenticationRequestModel request = createAuthenticationRequest("name", "password");
        registerUser(restTemplate, request);
    }


    @Test
    void testAuthenticate_Success() {
        // Given
        AuthenticationRequestModel request = createAuthenticationRequest("name", "password");
        // When
        ResponseEntity<AuthenticationResponseModel> response = restTemplate.postForEntity("/api/v1/authentication", request, AuthenticationResponseModel.class);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAuthenticate_UserBadCredentialException() {
        // Given
        AuthenticationRequestModel request = createAuthenticationRequest("names", "passwords");
        // When
        ResponseEntity<AuthenticationResponseModel> response = restTemplate.postForEntity("/api/v1/authentication", request, AuthenticationResponseModel.class);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
