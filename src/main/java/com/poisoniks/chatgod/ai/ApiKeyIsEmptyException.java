package com.poisoniks.chatgod.ai;

public class ApiKeyIsEmptyException extends RuntimeException {
    public ApiKeyIsEmptyException(String message) {
        super(message);
    }
}
