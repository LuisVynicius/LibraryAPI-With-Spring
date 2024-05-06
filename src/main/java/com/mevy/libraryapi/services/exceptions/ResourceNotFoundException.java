package com.mevy.libraryapi.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    
    public ResourceNotFoundException(Class<?> object, Object id){
        super(object.getSimpleName() + " Not found. Id: " + id);
    }

}
