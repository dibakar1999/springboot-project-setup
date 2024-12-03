package com.setupproject.setup.service;

import com.setupproject.setup.dto.AuthResponse;

/**
 * @Author Dibakar Chaudhary
 * 02/12/2024
 **/
public interface UserService {
    AuthResponse login(String username, String password);
}
