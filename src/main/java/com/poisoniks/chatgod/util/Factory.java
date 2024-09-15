package com.poisoniks.chatgod.util;

import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.ai.GPTConnector;
import com.poisoniks.chatgod.service.impl.ChatGodThread;
import com.poisoniks.chatgod.service.AIService;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.service.impl.AIServiceImpl;
import com.poisoniks.chatgod.service.impl.ChatManagerImpl;

public class Factory {
    private static final AIService aiService;
    private static final ChatManager chatManager;
    private static final ChatGodThread chatGodThread;
    private static final GPTConnector gptConnector;
    private static final ChatHelper chatHelper;

    static {
        gptConnector = new GPTConnector();
        chatManager = new ChatManagerImpl();
        chatHelper = new ChatHelper();
        aiService = new AIServiceImpl(chatManager, gptConnector, chatHelper);
        chatGodThread = new ChatGodThread(Config.rate, aiService);
    }

    public static AIService getAiService() {
        return aiService;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static ChatGodThread getChatGodThread() {
        return chatGodThread;
    }

    public static GPTConnector getGptConnector() {
        return gptConnector;
    }

    public static ChatHelper getChatHelper() {
        return chatHelper;
    }
}
