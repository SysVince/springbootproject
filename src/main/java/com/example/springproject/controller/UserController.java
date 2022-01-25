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
    public String helloPage() {
        return "HelloWorld";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable("id") int id, Principal principal) {

        try {
            AppUser appUser = appUserService.findUserById(id, principal);
            return ResponseEntity.ok(appUser);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tjena");
        }

    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUserDTO appUserDTO, Principal principal) throws Exception {
        if (principal.getName().equals("Admin")) {
            return appUserService.createUser(appUserDTO);
        } else {
            throw new SignatureException("Not admin");
        }

    }

    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable("id") int id, @RequestBody AppUserDTO appUserDTO, Principal principal) throws SignatureException {
        return appUserService.updateUser(id, appUserDTO, principal);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        return appUserService.deleteUser(id);
    }

    @GetMapping("/{id}/posts")
    public Post[] getUserPostsById(@PathVariable("id") int id, Principal principal) throws Exception {
        return appUserService.getUserPostsById(id, principal);
    }


}
