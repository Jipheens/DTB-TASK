package com.example.cardservice.Utils.exception;

public class InvalidRequestParameterException extends RuntimeException{

    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
