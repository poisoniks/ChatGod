package com.poisoniks.chatgod.service;

public interface AIService {
    void processChatHistory();
    void removeLatestMessagesBatch();
}
