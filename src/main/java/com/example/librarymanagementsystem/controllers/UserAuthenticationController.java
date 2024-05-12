package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.exceptions.UserBadCredentialException;
import com.example.librarymanagementsystem.model.AuthenticationCredentialsModel;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.AuthenticationResponseModel;
import com.example.librarymanagementsystem.model.StandardAddRequestResponseModel;
import com.example.librarymanagementsystem.services.UserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService service;

    @Operation(summary = "register  user for the first time ")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthenticationCredentialsModel request
    ) {
        try {
            log.info("User registration successful " + request);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/authenticate/register").toUriString());
            service.registerUser(request);
            return ResponseEntity.created(uri).body(StandardAddRequestResponseModel.builder().build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Operation(summary = "sign in  user using username and password")
    @PostMapping("")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestModel request
    ) {
        try {
            AuthenticationResponseModel authenticationResponseModel = service.authenticate(request);
            return ResponseEntity.ok().body(authenticationResponseModel);
        } catch (UserBadCredentialException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}