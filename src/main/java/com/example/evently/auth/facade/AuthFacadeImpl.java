package com.example.evently.auth.facade;

import com.example.evently.models.Role;
import com.example.evently.models.User;
import com.example.evently.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthFacadeImpl implements AuthFacade {

    AuthRepository authRepository;

    @Autowired
    public AuthFacadeImpl(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    public Optional<User> getAuthUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authRepository.findByUsername(username);
    }

    @Override
    public boolean isAdmin() {
        return this.getAuthUser().get().getRoles()
                .stream()
                .filter(r -> r.getName().equals(Role.RoleName.ROLE_ADMIN))
                .findAny().isPresent();
    }
}
