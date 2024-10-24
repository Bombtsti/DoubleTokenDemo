package com.example.demo.exception;

import lombok.Data;

@Data
public class TokenException extends RuntimeException{

    private Integer code;

    public TokenException(String message, Integer code){
        super(message);
        this.code = code;
    }

}
