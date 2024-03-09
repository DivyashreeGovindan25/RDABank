package com.RDABank.RDABank.Exception;

public class AccountDoesnotExistException extends RuntimeException{
    public AccountDoesnotExistException(String message){
        super(message);
    }
}
