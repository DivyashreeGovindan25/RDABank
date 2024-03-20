package com.RDABank.RDABank.Exception;

public class InvalidExpiryDate extends RuntimeException{
    public InvalidExpiryDate(String message){
        super(message);
    }
}
