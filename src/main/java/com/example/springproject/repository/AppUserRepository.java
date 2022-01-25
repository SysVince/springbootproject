package com.example.springproject.repository;


import com.example.springproject.model.AppUser;
import com.example.springproject.model.AppUserDTO;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class AppUserRepository {

    List<AppUser> appUsers = new ArrayList<>(List.of(
            new AppUser("Admin", "Password")
    ));


    public AppUser findUserById(int id, Principal principal) throws SignatureException {

        AppUser appUser2 = findUserByUsername(principal.getName());
        if (appUser2.getId() == id) {
            return appUsers.stream()
                    .filter(appUser -> appUser.getId() == id)
                    .findFirst()
                    .orElseThrow();
        }
        else {
            throw new SignatureException("Wrong signature");
        }
    }



    public AppUser createUser(AppUser appUser) throws Exception {

        for (AppUser user : appUsers) {
            if (user.getUsername().equals(appUser.getUsername())) {
                throw new Exception("Username Already Exist");
            }
        }
        appUsers.add(appUser);
        return appUsers.get(appUsers.indexOf(appUser));

    }


    public AppUser updateUser(AppUser appUser) {
        int indexOfUser = appUsers.indexOf(appUser);
        return appUsers.set(indexOfUser, appUser);
    }

    public String deleteUser(int id) {
        String username = appUsers
                .stream()
                .filter(appUser -> appUser.getId() == id)
                .findFirst()
                .orElseThrow()
                .getUsername();

        appUsers.removeIf(appUser -> appUser.getId() == id);
        return "The user: " + username + " has been deleted.";

    }

    public AppUser findUserByUsername(String username) {
        return appUsers
                .stream()
                .filter(appUser -> appUser.getUsername().equals(username))
                .findFirst()
                .orElseThrow();
    }

}
