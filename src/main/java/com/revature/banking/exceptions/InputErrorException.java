package com.revature.banking.exceptions;

public class InputErrorException extends RuntimeException{

    public InputErrorException(String message) {
        super(message);
    }
}
