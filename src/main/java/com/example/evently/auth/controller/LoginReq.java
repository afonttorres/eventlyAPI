package com.example.evently.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    @NotBlank(message = "Username is mandatory!")
    @Size(min=2, max=50, message = "Username should have 2 to 50 characters!")
    private String username;
    @NotBlank(message = "Password is mandatory!")
    @Size(min=2, max=50, message = "Password should have 7 to 50 characters!")
    private String password;
}
