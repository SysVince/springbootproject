package com.example.springproject.service;


import com.example.springproject.model.AuthDTO;
import com.example.springproject.repository.AppUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    public ResponseEntity<?> createToken(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword())
            );

            int id = appUserRepository.findUserByUsername(authDTO.getUsername()).getId();
            return ResponseEntity.ok(jwtHelper.createToken(authDTO, id));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
        }
    }

    public ResponseEntity<?> validateToken(String token) {
        try {
            Claims claims = jwtHelper.validateToken(token);
            return ResponseEntity.ok("Hej " + claims.getSubject());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
