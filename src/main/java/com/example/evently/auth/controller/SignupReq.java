package com.example.evently.auth.controller;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class SignupReq {

    @NotBlank(message = "Username is mandatory!")
    @Size(min=2, max=50, message = "Username should have 2 to 50 characters!")
    private String username;

    @NotBlank(message = "Name is mandatory!")
    @Size(min=2, max=50, message = "Name should have 2 to 80 characters!")
    private String name;

    @NotBlank(message = "Surname is mandatory!")
    @Size(min=2, max=50, message = "Surname should have 2 to 80 characters!")
    private String surname;

    @NotBlank(message = "Email is mandatory!")
    @Size(max = 50, message = "Email should have 50 characters maximum!")
    @Email
    private String email;

    private Set<String> role;

    @NotBlank(message = "Password is mandatory!")
    @Size(min=2, max=50, message = "Name should have 7 to 50 characters!")
    private String password;
}
