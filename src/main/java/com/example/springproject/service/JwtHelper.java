package com.example.springproject.service;


import com.example.springproject.model.AuthDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class JwtHelper {


    String nyckel = "This very long string is being encoded into sequence of bytes and then used as a secretkey";
    SecretKey secretKey = new SecretKeySpec(nyckel.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
    );

    public String createToken(AuthDTO authDTO,int id) {

        return Jwts.builder()
                .setSubject(authDTO.getUsername())
                .setId(String.valueOf(id))
                .claim("ROLE", "ADMIN")
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    

}
