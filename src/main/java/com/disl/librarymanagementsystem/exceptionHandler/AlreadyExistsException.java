package com.disl.librarymanagementsystem.exceptionHandler;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
