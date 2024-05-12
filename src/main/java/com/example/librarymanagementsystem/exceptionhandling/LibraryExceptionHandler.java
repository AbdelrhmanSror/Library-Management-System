package com.example.librarymanagementsystem.exceptionhandling;

import com.example.librarymanagementsystem.exceptions.CustomException;
import com.example.librarymanagementsystem.model.ErrorResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class LibraryExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseModel> handleCustomException(CustomException ex) {
        ErrorResponseModel errorResponse = ErrorResponseModel.builder()
                .timestamp(LocalDateTime.now())
                .customCode(ex.getCustomCode())
                .message(ex.getMessage())
                .build();
        log.error("Error response: {}", errorResponse);
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}
