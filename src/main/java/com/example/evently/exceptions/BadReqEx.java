package com.example.evently.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BadReqEx  extends RuntimeException{
    private String code;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadReqEx(String message, String code){
        super(message);
        this.code = code;
    }

}
