package com.example.librarymanagementsystem.config;

import com.example.librarymanagementsystem.exceptions.UserNotFoundException;
import com.example.librarymanagementsystem.repos.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserDetailsConfig {
    private final UserCredentialRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findById(Long.valueOf(username))
                .orElseThrow(UserNotFoundException::new);
    }
}