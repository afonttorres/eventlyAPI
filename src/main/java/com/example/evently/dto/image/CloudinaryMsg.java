package com.example.evently.dto.image;

import lombok.Data;

@Data
public class CloudinaryMsg {
    private String message;

    public CloudinaryMsg(String message) {
        this.message = message;
    }
}
