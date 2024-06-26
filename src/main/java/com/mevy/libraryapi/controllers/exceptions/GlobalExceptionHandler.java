package com.mevy.libraryapi.controllers.exceptions;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mevy.libraryapi.services.exceptions.DataBindingViolationException;
import com.mevy.libraryapi.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler{

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
                    MethodArgumentNotValidException  ex,
                    HttpHeaders headers,
                    HttpStatusCode status,
                    WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                 "Validation error. Check 'errors' field for details. ");
        
        for (FieldError field : ex.getBindingResult().getFieldErrors()){
            errorResponse.addValidationError(field.getField(), field.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
                    Exception exception,
                    WebRequest request){
        final String errorMessage = "Unknow error occurred";
        log.error(errorMessage, exception);
        return buildErrorResponse(
                    exception,
                    errorMessage,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
                    DataIntegrityViolationException dataIntegrityViolationException,
                    WebRequest request){
        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        log.error("Failed to save entity with integrity problems: " + errorMessage, dataIntegrityViolationException);
        return buildErrorResponse(
                    dataIntegrityViolationException,
                    errorMessage,
                    HttpStatus.CONFLICT,
                    request);
    }

    @ExceptionHandler(DataBindingViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataBindingViolationException(
                    DataBindingViolationException dataBindingViolationException,
                    WebRequest request){
        log.error("Failed to remove a entity: " + dataBindingViolationException);
        return buildErrorResponse(
                        dataBindingViolationException,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
                    ConstraintViolationException constraintViolationException,
                    WebRequest request){
        log.error("Failed to validate element", constraintViolationException);
        return buildErrorResponse(
                        constraintViolationException,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
        ResourceNotFoundException resourceNotFoundException,
        WebRequest request){
        log.error("Failed to find a resource", resourceNotFoundException);
        return buildErrorResponse(
                        resourceNotFoundException,
                        HttpStatus.NOT_FOUND,
                        request);
    }

    private ResponseEntity<Object> buildErrorResponse(
                    Exception exception,
                    String message,
                    HttpStatus status,
                    WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        if (printStackTrace){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(
                    Exception exception,
                    HttpStatus status,
                    WebRequest request){
        return buildErrorResponse(
                        exception,
                        exception.getMessage(),
                        status,
                        request);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        Integer status = HttpStatus.UNAUTHORIZED.value();
        response.setStatus(status);
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(status, "Some credential is invalid. ");
        response.getWriter().write(errorResponse.toJson());
    }
    
}