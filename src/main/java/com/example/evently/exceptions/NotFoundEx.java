package com.example.evently.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundEx extends RuntimeException{
    private String code;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundEx(String message, String code){
        super(message);
        this.code = code;
    }
}
