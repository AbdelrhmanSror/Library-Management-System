package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dtos.PatronDTO;

import java.util.List;

public interface PatronService {
    List<PatronDTO> getAllPatrons();

    PatronDTO getPatronById(Long id);

    void addPatron(PatronDTO patron);

    void updatePatron(Long id, PatronDTO patron);

    void deletePatron(Long id);
}
