package com.example.evently.auth.facade;

import com.example.evently.models.User;
import com.example.evently.auth.config.repositories.AuthRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthFacadeImpl implements AuthFacade {

    AuthRepository authRepository;

    public AuthFacadeImpl(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    public Optional<User> getAuthUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authRepository.findByUsername(username);
    }
}
