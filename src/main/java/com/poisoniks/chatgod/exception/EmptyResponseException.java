package com.poisoniks.chatgod.exception;

public class EmptyResponseException extends RuntimeException {
    public EmptyResponseException(String message) {
        super(message);
    }
    public EmptyResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
