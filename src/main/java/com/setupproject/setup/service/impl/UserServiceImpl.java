package com.setupproject.setup.service.impl;

import com.setupproject.setup.config.MyUserDetailService;
import com.setupproject.setup.controller.AuthResource;
import com.setupproject.setup.dto.AuthResponse;
import com.setupproject.setup.service.JwtTokenGeneratorService;
import com.setupproject.setup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @Author Dibakar Chaudhary
 * 02/12/2024
 **/
@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGeneratorService jwtTokenGeneratorService;
    private final MyUserDetailService myUserDetailService;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenGeneratorService jwtTokenGeneratorService, MyUserDetailService myUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenGeneratorService = jwtTokenGeneratorService;
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    public AuthResponse login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);
        boolean authenticated = authenticate.isAuthenticated();
        if (!authenticated) {
            new IllegalArgumentException("Invalid username or password");
        }
        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
        String token = jwtTokenGeneratorService.generateToken(userDetails);
        return new AuthResponse(username, token);
    }
}
