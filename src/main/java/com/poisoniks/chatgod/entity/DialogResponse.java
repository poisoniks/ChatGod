package com.poisoniks.chatgod.entity;

public class DialogResponse implements AIResponse {
    private final String message;

    public DialogResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
