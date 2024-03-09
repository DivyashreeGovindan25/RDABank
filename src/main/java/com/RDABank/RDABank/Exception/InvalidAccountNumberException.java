package com.RDABank.RDABank.Exception;

public class InvalidAccountNumberException extends RuntimeException{
    public InvalidAccountNumberException(String message){
        super(message);
    }
}