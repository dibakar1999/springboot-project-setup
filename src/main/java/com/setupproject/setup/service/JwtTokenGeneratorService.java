package com.setupproject.setup.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author Dibakar Chaudhary
 * 02/12/2024
 **/
public interface JwtTokenGeneratorService {
    String generateToken(String username);

    String getUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
