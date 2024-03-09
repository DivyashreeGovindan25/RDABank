package com.RDABank.RDABank.Exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String msg){
        super(msg);
    }
}