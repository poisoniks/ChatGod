package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.service.AIService;

public class ChatGodThreadController {
    private final long rate;
    private final AIService aiService;
    private Thread chatGodThread;

    public ChatGodThreadController(long rate, AIService aiService) {
        this.rate = rate;
        this.aiService = aiService;
    }

    public synchronized void start() {
        if (chatGodThread == null || !chatGodThread.isAlive()) {
            ChatGodThread chatGodRunnable = new ChatGodThread(rate, aiService);
            chatGodThread = new Thread(chatGodRunnable);
            chatGodThread.start();
        }
    }

    public synchronized void stop() {
        if (chatGodThread != null && chatGodThread.isAlive()) {
            chatGodThread.interrupt();
            try {
                chatGodThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void clear() {
        aiService.clearChatHistory();
    }
}
