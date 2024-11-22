package com.poisoniks.chatgod.action;

import com.poisoniks.chatgod.entity.AIResponse;

public class DialogResponse implements AIResponse {
    private final String message;

    public DialogResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
