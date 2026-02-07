package com.example.UserAuthenticationService.Exceptions;

public class IncorrectPassword extends RuntimeException{

    public IncorrectPassword(String message){
        super(message);
    }
}
