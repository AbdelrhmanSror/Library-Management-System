package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.exceptions.UserBadCredentialException;
import com.example.librarymanagementsystem.model.AuthenticationRequestModel;
import com.example.librarymanagementsystem.model.JwtToken;
import com.example.librarymanagementsystem.repos.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserCredentialRepository userCredentialRepository;
    @Value("${ACCESS_TOKEN_EXPIRATION_DATE_SECONDS}")
    private String tokenExpirationDate;


    private JwtToken generateToken(String subject, Map<String, Object> extraClaims, long expirationDate) {
        return jwtService.generateToken(extraClaims
                , subject, expirationDate);
    }


    public JwtToken authenticateUser(AuthenticationRequestModel request) {
        // Find the user by username in the repository
        var user = userCredentialRepository.findByUsername(request.getUsername()).orElseThrow(UserBadCredentialException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserBadCredentialException();

        }

        // Generate and return a token with the user id, type, and expiration date
        return generateToken(String.valueOf(user.getId()), Map.of("name", user.getUsername(), "id", user.getId())
                , Long.decode(tokenExpirationDate));

    }


}