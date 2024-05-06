package com.mevy.libraryapi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DataBindingViolationException extends RuntimeException{
    
    public DataBindingViolationException(){
        super("The entity cannot be deleted because it has a relationship with another entity. ");
    }

}
