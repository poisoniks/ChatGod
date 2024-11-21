package com.poisoniks.chatgod.service;

public interface AIService {
    void doAction();
    void removeLatestMessagesBatch();
    void clearChatHistory();
}
