package com.example.springproject.SecurityConfig;


import com.example.springproject.service.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    @Autowired
    JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //Om header finns
        if(authHeader != null){
            //Tar bort ordet "bearer " i "Bearer token" som är 7 chars innan token börjar
            String token = authHeader.substring(7);
            //Validerar token och lägger i string username, är det fel throwar det exception
            String username = jwtHelper.validateToken(token).getSubject();

            //Skapar en object
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,null);

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Det är här spring håller koll på vilka som är och inte är inloggad och vem har rätt och göra vad
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }


        filterChain.doFilter(request,response);

    }
}
