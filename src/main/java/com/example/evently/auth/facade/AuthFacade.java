package com.example.evently.auth.facade;

import com.example.evently.models.user.User;

import java.util.Optional;

public interface AuthFacade {
    public Optional<User> getAuthUser();
}
