package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.exceptions.UserBadCredentialException;
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


@RestController
@Slf4j
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService service;

    @Operation(summary = "register  user for the first time ")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthenticationRequestModel request
    ) {
        service.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(StandardAddRequestResponseModel.builder().build());

    }

    @Operation(summary = "sign in  user using username and password")
    @PostMapping("")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestModel request
    ) {
        AuthenticationResponseModel authenticationResponseModel = service.authenticate(request);
        log.info("authenticationResponseModel: " + authenticationResponseModel);
        return ResponseEntity.ok().body(authenticationResponseModel);

    }


}