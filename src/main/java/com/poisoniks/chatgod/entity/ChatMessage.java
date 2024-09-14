package com.poisoniks.chatgod.entity;

public class ChatMessage {
    private final String message;
    private final String sender;
    private final long time;
    private final ChatMessageType type;

    public ChatMessage(String message, String sender, long time, ChatMessageType type) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public long getTime() {
        return time;
    }

    public ChatMessageType getType() {
        return type;
    }

    public String getStringTime() {
        if (time <= 0) {
            return "00:00:00";
        }
        return String.format("%02d:%02d:%02d", time / 3600000 % 24, time / 60000 % 60, time / 1000 % 60);
    }
}
