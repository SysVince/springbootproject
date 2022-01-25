package com.example.springproject.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {

    String username;
    String password;

    public AppUser toUser() {
        return new AppUser(username, password);
    }
}
