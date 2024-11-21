package com.poisoniks.chatgod.exception;

public class ApiKeyIsEmptyException extends RuntimeException {
    public ApiKeyIsEmptyException(String message) {
        super(message);
    }
}
