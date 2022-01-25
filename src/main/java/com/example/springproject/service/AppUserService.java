package com.example.springproject.service;


import com.example.springproject.model.Post;
import com.example.springproject.model.AppUser;
import com.example.springproject.model.AppUserDTO;
import com.example.springproject.repository.AppUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.security.Principal;
import java.security.SignatureException;

@Service
public class AppUserService {


    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    WebClient webClient;


    public AppUser findUserById(int id, Principal principal) throws SignatureException {

        return appUserRepository.findUserById(id,principal);
    }

    public AppUser createUser(AppUserDTO appUserDTO) throws Exception {
        AppUser appUser = appUserDTO.toUser();
        return appUserRepository.createUser(appUser);
    }

    public AppUser updateUser(int id, AppUserDTO appUserDTO, Principal principal) throws SignatureException {
        AppUser appUser = appUserRepository.findUserById(id, principal);
        BeanUtils.copyProperties(appUserDTO, appUser);
        return appUserRepository.updateUser(appUser);
    }

    public String deleteUser(int id) {

        return appUserRepository.deleteUser(id);
    }

    public Post[] getUserPostsById(int id, Principal principal) throws SignatureException {
        AppUser appUser = appUserRepository.findUserByUsername(principal.getName());
        if(appUser.getId()==id){
            return webClient
                    .get()
                    .uri("https://jsonplaceholder.typicode.com/posts?userId=" + id)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Post[].class))
                    .block();
        }
        else {
            throw new SignatureException("Missmatch");
        }

    }
}
