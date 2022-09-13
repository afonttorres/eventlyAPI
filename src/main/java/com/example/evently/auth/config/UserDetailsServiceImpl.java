package com.example.evently.auth.config;

import com.example.evently.models.user.User;
import com.example.evently.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    AuthRepository authRepository;

    @Autowired
    public UserDetailsServiceImpl(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + "Not Found"));

        return UserDetailsImpl.build(user);
    }
}
