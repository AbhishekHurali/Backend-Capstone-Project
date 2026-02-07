package com.example.ProductCatalogue.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//If we use this annotation then from all the different controllers when we get the particular exception
// then it will find the exception in this class and trigger it
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)//We can give as many number of exceptions as we can
    //use curly braces and comma separated.
    public ResponseEntity<String> IllegalArgumentException(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
//Use of Exception Handlers
//Centralizing exception handling via a global or controller-specific exception handler improves the robustness of applications by providing uniform error responses.
//Exception handlers can be categorized for various exception types like IllegalArgumentException, NullPointerException, etc. This avoids exposing stack traces to the users.
//Implementing an Exception Handler
//An example of an exception handler uses annotations such as @ExceptionHandler, which automatically intercepts exceptions thrown by the controller.
//It allows the application to handle exceptions gracefully and provide meaningful HTTP responses instead of generic error messages