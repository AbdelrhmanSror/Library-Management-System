package com.example.librarymanagementsystem.constants;


import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Pattern;


@Slf4j
public class PathUri {
    public static final List<String> PermittedPathUris = List.of("/api/v1/authentication/**",
            "/swagger-ui/**",
            "/v3/api-docs/**");

    public static boolean isUriPermitted(String uri) {
        for (String permittedUriPattern : PermittedPathUris) {
            String regexPattern = permittedUriPattern.replace("**", ".*");
            String regexPattern2 = permittedUriPattern.replace("/**", "");

            // Check if the given URI matches the pattern
            if (Pattern.matches(regexPattern, uri) || Pattern.matches(regexPattern2, uri)) {
                return true;
            }
        }
        return false;
    }
}