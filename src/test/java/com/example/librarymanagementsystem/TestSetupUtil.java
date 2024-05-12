package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.dtos.BookDTO;
import com.example.librarymanagementsystem.dtos.PatronDTO;
import com.example.librarymanagementsystem.model.*;


import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.StandardAddRequestResponseModel;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSetupUtil {

    public static AuthenticationRequestModel createAuthenticationRequest(String username, String password) {
        AuthenticationRequestModel request = new AuthenticationRequestModel();
        request.setUsername(username);
        request.setPassword(password);
        return request;
    }

    public static void registerUser(TestRestTemplate restTemplate, AuthenticationRequestModel requestModel) {

        restTemplate.postForEntity("/api/v1/authentication/register", requestModel, StandardAddRequestResponseModel.class);
    }

    public static AuthenticationResponseModel authenticateUser(TestRestTemplate restTemplate, AuthenticationRequestModel requestModel) {
        ResponseEntity<AuthenticationResponseModel> response = restTemplate.postForEntity("/api/v1/authentication", requestModel, AuthenticationResponseModel.class);
        return response.getBody();
    }

    public static String createBook(TestRestTemplate restTemplate, HttpEntity<BookDTO> entity) {
        BookDTO bookDTO = new BookDTO();
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/books", entity, String.class);
        return response.getBody();
    }

    public static String createPatron(TestRestTemplate restTemplate, HttpEntity<PatronDTO> entity) {
        PatronDTO patronDTO = new PatronDTO();
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/patrons", entity, String.class);
        return response.getBody();
    }

}

