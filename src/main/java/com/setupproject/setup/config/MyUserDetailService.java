package com.setupproject.setup.config;

import com.setupproject.setup.model.UserPrincipal;
import com.setupproject.setup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found!"));
        return new UserPrincipal(users);
    }
}
