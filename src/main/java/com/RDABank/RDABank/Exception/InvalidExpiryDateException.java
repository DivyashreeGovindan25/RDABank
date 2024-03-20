package com.RDABank.RDABank.Exception;

public class InvalidExpiryDateException extends RuntimeException{
    public InvalidExpiryDateException(String message){
        super(message);
    }
}
