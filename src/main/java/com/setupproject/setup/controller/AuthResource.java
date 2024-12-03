package com.setupproject.setup.controller;

import com.setupproject.setup.common.Response;
import com.setupproject.setup.dto.AuthRequest;
import com.setupproject.setup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    private final UserService userService;

    @Autowired
    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response<Object> login(@RequestBody AuthRequest request) {
        try {
            var response = userService.login(request.getUsername(), request.getPassword());
            return new Response<>("00", "Success", response);
        } catch (Exception e) {
            return new Response<>("01", e.getMessage());
        }
    }
}
