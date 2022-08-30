package com.example.evently.auth.controller;

import lombok.Data;

@Data
public class MessageRes {
    private String message;


    public MessageRes(String message) {
        this.message = message;
    }


}
