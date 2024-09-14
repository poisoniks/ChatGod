package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.ChatGod;
import com.poisoniks.chatgod.ai.FatalException;
import com.poisoniks.chatgod.service.AIService;

public class ChatGodThread implements Runnable {
    private final long rate;
    private final AIService aiService;
    private byte numOfErrors;

    public ChatGodThread(long rate, AIService aiService) {
        this.rate = rate;
        this.aiService = aiService;
        this.numOfErrors = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(rate);
            } catch (InterruptedException e) {
                break;
            }
            try {
                aiService.processChatHistory();
            } catch (FatalException e) {
                ChatGod.LOG.error("Unrecoverable error, shutting down!", e);
                break;
            } catch (Exception e) {
                numOfErrors++;
                ChatGod.LOG.error("ChatGod broke, check the logs!", e);
                if (numOfErrors > 5) {
                    ChatGod.LOG.error("ChatGod broke too many times, shutting down!");
                    break;
                }
            }
        }
    }
}
