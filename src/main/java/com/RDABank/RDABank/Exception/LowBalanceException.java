package com.RDABank.RDABank.Exception;

public class LowBalanceException extends RuntimeException{
    public LowBalanceException(String message){
        super(message);
    }
}
