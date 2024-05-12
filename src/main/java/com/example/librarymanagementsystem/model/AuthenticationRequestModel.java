package com.example.librarymanagementsystem.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestModel {
    @NonNull
    private String username;
    @NonNull
    String password;


}