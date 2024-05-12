package com.example.librarymanagementsystem.repos;


import com.example.librarymanagementsystem.entities.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

    Boolean existsByUsername(String username);

    Optional<UserCredential> findByUsername(String username);

}