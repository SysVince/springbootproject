package com.example.springproject.controller;


import com.example.springproject.model.AppUser;
import com.example.springproject.model.Post;
import com.example.springproject.model.AppUserDTO;
import com.example.springproject.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.SignatureException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    AppUserService appUserService;


    @GetMapping("/hello")
    public String helloWorldPage() {
        return "HelloWorld";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") int id, Principal principal) {

        try {
            AppUser appUser = appUserService.findUserById(id, principal);
            return ResponseEntity.ok(appUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You dont have access to this.");
        }

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody AppUserDTO appUserDTO, Principal principal) {
        try {
            if (principal.getName().equals("Admin")) {
                AppUser appUser = appUserService.createUser(appUserDTO);
                return ResponseEntity.ok(appUser);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please contact an admin to use this function");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username already exists. Please use another one.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody AppUserDTO appUserDTO, Principal principal) {
        try {
            AppUser appUser = appUserService.updateUser(id, appUserDTO, principal);
            return ResponseEntity.ok(appUser);
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You dont have access to this.");
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        return appUserService.deleteUser(id);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<?> getUserPostsById(@PathVariable("id") int id, Principal principal) {
        try {
            Post[] post = appUserService.getUserPostsById(id, principal);
            return ResponseEntity.ok(post);
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You dont have access to this.");
        }
    }


}
