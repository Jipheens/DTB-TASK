package com.example.accountservice.Utils.exception;

public class InvalidRequestParameterException extends RuntimeException{

    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
