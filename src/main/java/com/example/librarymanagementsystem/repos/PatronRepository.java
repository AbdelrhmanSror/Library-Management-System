package com.example.librarymanagementsystem.repos;

import com.example.librarymanagementsystem.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
