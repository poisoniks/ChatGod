package com.poisoniks.chatgod.util;

import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.ai.GPTConnector;
import com.poisoniks.chatgod.command.ChatGodControlCommand;
import com.poisoniks.chatgod.service.AIService;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.service.impl.AIServiceImpl;
import com.poisoniks.chatgod.service.impl.ChatActivityListener;
import com.poisoniks.chatgod.service.impl.ChatGodThreadController;
import com.poisoniks.chatgod.service.impl.ChatManagerImpl;

public class Factory {
    private static final AIService aiService;
    private static final ChatManager chatManager;
    private static final GPTConnector gptConnector;
    private static final ChatHelper chatHelper;
    private static final ChatGodThreadController chatGodThreadController;
    private static final ChatGodControlCommand chatGodControlCommand;
    private static final ChatActivityListener chatActivityListener;

    static {
        gptConnector = new GPTConnector();
        chatManager = new ChatManagerImpl();
        chatHelper = new ChatHelper();
        aiService = new AIServiceImpl(chatManager, gptConnector, chatHelper);
        chatGodThreadController = new ChatGodThreadController(Config.rate, aiService);
        chatGodControlCommand = new ChatGodControlCommand(chatGodThreadController);
        chatActivityListener = new ChatActivityListener(chatManager);
    }

    public static AIService getAiService() {
        return aiService;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static GPTConnector getGptConnector() {
        return gptConnector;
    }

    public static ChatHelper getChatHelper() {
        return chatHelper;
    }

    public static ChatGodThreadController getChatGodThreadController() {
        return chatGodThreadController;
    }

    public static ChatGodControlCommand getChatGodControlCommand() {
        return chatGodControlCommand;
    }
    public static ChatActivityListener getChatActivityListener() {
        return chatActivityListener;
    }
}
