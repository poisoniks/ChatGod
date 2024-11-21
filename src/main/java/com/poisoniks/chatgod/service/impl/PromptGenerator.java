package com.poisoniks.chatgod.service.impl;

import java.util.List;

public class PromptGenerator {
    private static final String PROMPT_BASE = """
            You are a chat God on the minecraft server.
            You are proud, but funny and sarcastic. You can be a bit of a troll, but you are not mean.
            Your main goal is to entertain the players on the server.
            """;
    private static final String SERVER_SPECIFIC_CONTEXT = "Server specific context:\n%s";
    private static final String LIST_OF_PLAYERS_ONLINE = "List of players online:\n%s";
    private static final String CHAT_HISTORY = "Adding minecraft chat log with time stamps. Messages encapsulated in '*' are for context. " +
        "Messages from 'ChatGod' are yours:\n%s";

    private final StringBuilder content;

    private PromptGenerator() {
        this.content = new StringBuilder(PROMPT_BASE);
    }

    public PromptGenerator withServerSpecificContext(String context) {
        content.append(String.format(SERVER_SPECIFIC_CONTEXT, context));
        return this;
    }

    public PromptGenerator withListOfPlayersOnline(List<String> players) {
        content.append(String.format(LIST_OF_PLAYERS_ONLINE, players));
        return this;
    }

    public PromptGenerator withChatHistory(String chatHistory) {
        content.append(String.format(CHAT_HISTORY, chatHistory));
        return this;
    }

    public PromptGenerator withTask(String task) {
        content.append(task);
        return this;
    }

    public String build() {
        return content.toString();
    }

    public static PromptGenerator prompt() {
        return new PromptGenerator();
    }
}
