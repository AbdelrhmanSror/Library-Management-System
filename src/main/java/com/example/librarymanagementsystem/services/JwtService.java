package com.example.librarymanagementsystem.services;


import com.example.librarymanagementsystem.constants.TokenStatusEnum;
import com.example.librarymanagementsystem.model.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${JWT_SECRET_KEY}")
    String JwtSecretKey;

    public String extractUserName(JwtToken jwtToken) {
        if (jwtToken == null) {
            // Handle null JWT token, e.g., throw an exception or return a default value
            throw new IllegalArgumentException("JWT token is null");
        }
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String extractAttribute(JwtToken jwtToken, String attributeName) {
        if (jwtToken == null) {
            // Handle null JWT token, e.g., throw an exception or return a default value
            throw new IllegalArgumentException("JWT token is null");
        }
        log.info("Extracting " + attributeName + " from " + jwtToken);
        return extractClaim(jwtToken, claims -> {
            if (claims == null) {
                // Handle null claims, e.g., throw an exception or return a default value
                throw new IllegalArgumentException("Claims is null");
            }
            return String.valueOf(claims.get(attributeName));
        });
    }

    private <T> T extractClaim(JwtToken jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public JwtToken generateToken(
            Map<String, Object> extraClaims,
            String subject,
            Long expirationTimeInSeconds
    ) {
        log.info("expiration time of jwt is {} seconds", expirationTimeInSeconds);

        Date expirationDate = new Date(System.currentTimeMillis() + (expirationTimeInSeconds * 1000));

        return JwtToken.builder().token(Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact()).build();
    }


    public TokenStatusEnum getTokenStatus(JwtToken jwtToken) {
        if (jwtToken == null) {
            return TokenStatusEnum.NOT_VALID;
        }
        try {
            if (isTokenExpired(jwtToken))
                return TokenStatusEnum.EXPIRED;
            return TokenStatusEnum.VALID;
        } catch (Exception e) {
            log.info("exception not valid {}  {}", e.getMessage(), jwtToken);
            return TokenStatusEnum.NOT_VALID;
        }
    }

    public boolean isTokenValid(JwtToken jwtToken, UserDetails userDetails) {
        final String username = extractUserName(jwtToken);
        log.info("is token valid {} {}   {} {}", username, userDetails.getUsername(), userDetails, (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken));
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(JwtToken jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(JwtToken jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(JwtToken jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken.getToken())
                .getBody();

    }

    private Key getSignInKey() {
        log.info("jws: getSignInKey called {} ", JwtSecretKey);
        byte[] keyBytes = Decoders.BASE64.decode(JwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}