package com.setupproject.setup.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @Author Dibakar Chaudhary
 * 02/12/2024
 **/
public interface JwtTokenGeneratorService {
    String generateToken(UserDetails userDetails);

    String getUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
    List<SimpleGrantedAuthority> extractRoles(String token);
}
