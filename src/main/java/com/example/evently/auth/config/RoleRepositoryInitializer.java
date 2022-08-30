package com.example.evently.auth.config;

import com.example.evently.models.Role;
import com.example.evently.models.User;
import com.example.evently.repositories.AuthRepository;
import com.example.evently.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

 @Component
public class RoleRepositoryInitializer {
    private RoleRepository roleRepository;
    private AuthRepository authRepository;
    private PasswordEncoder encoder;

    @Autowired
    public RoleRepositoryInitializer(RoleRepository roleRepository, AuthRepository authRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void addAvailableRoles() {
        if (!roleRepository.findAll().isEmpty()) {
            return;
        }


        List<Role> roles = List.of(
                new Role(1, Role.RoleName.ROLE_ADMIN),
                new Role(2, Role.RoleName.ROLE_USER)
        );

        roleRepository.saveAll(roles);

        if (!authRepository.findAll().isEmpty()) {
            return;
        }

        var user = new User();
        user.setRoles(roleRepository.findAll().stream().collect(Collectors.toSet()));
        user.setEmail("admin@admin.com");
        user.setUsername("admin");
        user.setPassword(encoder.encode("password1234"));

        authRepository.save(user);
    }

}
