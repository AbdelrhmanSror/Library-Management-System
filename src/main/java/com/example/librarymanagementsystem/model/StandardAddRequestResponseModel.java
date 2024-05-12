package com.example.librarymanagementsystem.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardAddRequestResponseModel {
    private  final String result="successfully added";

}