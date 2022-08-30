package com.example.evently.auth.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
