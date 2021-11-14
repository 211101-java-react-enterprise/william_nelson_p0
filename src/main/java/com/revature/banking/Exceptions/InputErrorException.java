package com.revature.banking.Exceptions;

public class InputErrorException extends RuntimeException{

    public InputErrorException(String message) {
        super(message);
    }
}
