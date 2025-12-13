package com.learn.spring.tickets.config;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class JwtUtils {

    private JwtUtils(){

    }

    public static UUID getUserID(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }


}
