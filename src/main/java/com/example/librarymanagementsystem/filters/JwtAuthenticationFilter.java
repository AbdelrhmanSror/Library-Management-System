package com.example.librarymanagementsystem.filters;


import com.example.librarymanagementsystem.constants.PathUri;
import com.example.librarymanagementsystem.model.JwtToken;
import com.example.librarymanagementsystem.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {
            String requestURI = request.getRequestURI();
            if (PathUri.isUriPermitted(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
            log.info(request.getRequestURI());
            final String authHeader = request.getHeader("Authorization");
            final JwtToken jwtFromRequest = authHeader != null && (authHeader.startsWith("bearer") || authHeader.startsWith("Bearer")) ? JwtToken.builder().token(authHeader.substring(7)).build() : null;
            final String userId = jwtService.extractUserName(jwtFromRequest);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = geUserDetails(userId);


                if (jwtService.isTokenValid(jwtFromRequest, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }

            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            //The 498 Invalid Token status code is a client error that is sent by the server ,
            // when the client submits a HTTP request that does not include a valid token
            response.setStatus(498);
            response.getOutputStream().print("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            //Status code 401 - unauthorized / token expired
            response.setStatus(401);
            response.getOutputStream().print("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            //The HTTP 415 Unsupported Media Type client error response code indicates
            // that the server refuses to accept the request,
            // because the payload format is in an unsupported format
            response.setStatus(415);
            response.getOutputStream().print("JWT token is unsupported: {}" + e.getMessage());

        } catch (IllegalArgumentException e) {
            //400 Bad Request : Invalid argument (invalid request payload)
            response.setStatus(400);
            response.getOutputStream().print("JWT claims string is empty: {}" + e.getMessage());

        } catch (Exception e) {
            response.setStatus(400);
            writeErrorMessageToResponse(response, "Error in Accessing the resource: " + e.getMessage());

        }
    }


    private UserDetails geUserDetails(String userId) {
        return this.userDetailsService.loadUserByUsername(userId);
    }

    private void writeErrorMessageToResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println("{\"error\": \"" + errorMessage + "\"}");
        writer.flush();
    }
}