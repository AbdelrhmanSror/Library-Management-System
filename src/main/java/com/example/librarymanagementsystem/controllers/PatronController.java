package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.dtos.PatronDTO;
import com.example.librarymanagementsystem.services.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/patrons")
public class PatronController {

    private final PatronService patronService;

    @Operation(summary = "Get all patrons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of patrons retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No patrons found")
    })
    @GetMapping
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        List<PatronDTO> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }

    @Operation(summary = "Get a patron by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patron retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patron not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        PatronDTO patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }

    @Operation(summary = "Add a new patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patron created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Long> addPatron(@RequestBody PatronDTO patronDTO) {
        Long patronId = patronService.addPatron(patronDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patronId);
    }

    @Operation(summary = "Update an existing patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patron updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Patron not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePatron(@PathVariable Long id, @RequestBody PatronDTO patronDTO) {
        patronService.updatePatron(id, patronDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a patron by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patron deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patron not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok().build();
    }
}
