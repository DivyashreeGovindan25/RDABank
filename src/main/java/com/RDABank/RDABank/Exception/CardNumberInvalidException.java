package com.RDABank.RDABank.Exception;

public class CardNumberInvalidException extends RuntimeException {
    public CardNumberInvalidException(String message){
        super(message);
    }
}