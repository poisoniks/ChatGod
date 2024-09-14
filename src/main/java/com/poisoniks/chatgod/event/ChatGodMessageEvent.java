package com.poisoniks.chatgod.event;

import cpw.mods.fml.common.eventhandler.Event;

public class ChatGodMessageEvent extends Event {
    private final String message;

    public ChatGodMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
