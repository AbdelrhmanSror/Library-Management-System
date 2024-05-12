package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.entities.UserCredential;
import com.example.librarymanagementsystem.exceptions.UserNameDuplicateException;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.AuthenticationResponseModel;
import com.example.librarymanagementsystem.model.JwtToken;
import com.example.librarymanagementsystem.repos.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationService {
    private final CredentialService credentialService;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;


    public void registerUser(AuthenticationRequestModel request) {

        // Check if the username already exists in the repository and throw an exception if yes
        throwUserNameDuplicateExceptionIfUserWithNameExist(request.getUsername());

        // Create a user credential object with the request information and encode the password
        var user = UserCredential.builder()
                .username(request.getUsername())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .build();
        // Save the user credential to the repository
        userCredentialRepository.save(user);
    }

    private void throwUserNameDuplicateExceptionIfUserWithNameExist(String username) {
        if (isUserNameExist(username))
            throw new UserNameDuplicateException();
    }

    public boolean isUserNameExist(String username) {
        return userCredentialRepository.existsByUsername(username);
    }

    public AuthenticationResponseModel authenticate(AuthenticationRequestModel request) {

        JwtToken token = credentialService.authenticateUser(AuthenticationRequestModel.builder()
                .password(request.getPassword())
                .username(request.getUsername()).build());
        return AuthenticationResponseModel.builder()
                .token(token.getToken())
                .build();

    }


}