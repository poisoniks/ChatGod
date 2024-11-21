package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.ChatGod;
import com.poisoniks.chatgod.exception.FatalException;
import com.poisoniks.chatgod.service.AIService;
import com.poisoniks.chatgod.service.ActionStrategy;
import com.poisoniks.chatgod.service.ChatManager;

public class AIServiceImpl implements AIService {


    private final ChatManager chatManager;
    private final ActionStrategy actionStrategy;

    public AIServiceImpl(ChatManager chatManager, ActionStrategy actionStrategy) {
        this.chatManager = chatManager;
        this.actionStrategy = actionStrategy;
    }

    @Override
    public void doAction() {
        try {
            actionStrategy.executeAction();
        } catch (Exception e) {
            ChatGod.LOG.error("Error while executing action", e);
            throw new FatalException("Error while executing action", e);
        }
    }

    @Override
    public void removeLatestMessagesBatch() {
        chatManager.removeLatestMessagesBatch();
    }

    @Override
    public void clearChatHistory() {
        chatManager.clearChatMessages();
    }
}
