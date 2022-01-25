package com.example.springproject.controller;


import com.example.springproject.model.AuthDTO;
import com.example.springproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/token")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<?> createToken (@RequestBody AuthDTO authDTO){
        return authService.createToken(authDTO);
    }

    @GetMapping
    public ResponseEntity<?> validateToken(@RequestParam("token")String token){
        return authService.validateToken(token);
    }

}
