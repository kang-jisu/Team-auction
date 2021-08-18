package com.project.auction.lol.errors;

import org.springframework.http.HttpStatus;

public class MayoException extends RuntimeException{

    private HttpStatus httpStatus;

    public MayoException(HttpStatus httpStatus,String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
