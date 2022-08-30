package com.example.evently.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
