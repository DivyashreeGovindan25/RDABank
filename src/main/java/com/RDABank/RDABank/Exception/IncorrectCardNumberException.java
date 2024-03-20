package com.RDABank.RDABank.Exception;

public class IncorrectCardNumberException extends RuntimeException{
    public IncorrectCardNumberException(String message){
        super(message);
    }
}
