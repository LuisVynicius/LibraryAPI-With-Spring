package com.mevy.libraryapi.controllers.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ErrorResponse implements Serializable {
    
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> erros;

    @RequiredArgsConstructor
    @Getter
    @Setter
    private static class ValidationError{
        
        private final String field;
        private final String message;

    }

    public void addValidationError(String field, String message){
        if (Objects.isNull(erros)){
            erros = new ArrayList<ValidationError>();
        }
        erros.add(new ValidationError(field, message));
    }

    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }

}
