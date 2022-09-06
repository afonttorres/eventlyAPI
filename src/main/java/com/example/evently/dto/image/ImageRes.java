package com.example.evently.dto.image;

import lombok.Data;

@Data
public class ImageRes {
    private String message;
    private String url;

    private Long id;

    public ImageRes(String message, String url, Long id){
        this.message = message;
        this.url = url;
        this.id = id;
    }
}
