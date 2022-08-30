package com.example.evently.auth.controller;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class SignupReq {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 20)
    private String surname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 7, max = 40)
    private String password;
}
