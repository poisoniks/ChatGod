package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.entity.ChatMessage;
import com.poisoniks.chatgod.entity.ChatMessageType;
import com.poisoniks.chatgod.service.ChatManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatManagerImpl implements ChatManager {
    private final List<ChatMessage> chatMessages;
    private long lastMessageTime;

    public ChatManagerImpl() {
        this.chatMessages = Collections.synchronizedList(new LinkedList<>());
        this.lastMessageTime = 0;
    }

    @Override
    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    @Override
    public List<ChatMessage> getChatMessages(ChatMessageType... types) {
        return chatMessages.stream()
                .filter(message -> {
                    for (ChatMessageType type : types) {
                        if (message.getType() == type) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessage getLatestMessage() {
        return chatMessages.get(chatMessages.size() - 1);
    }

    @Override
    public ChatMessage getLatestMessage(ChatMessageType type) {
        return chatMessages.stream()
                .filter(message -> message.getType() == type)
                .reduce((first, second) -> second)
                .orElse(null);
    }

    @Override
    public long getLastMessageTime() {
        return lastMessageTime;
    }

    @Override
    public long getLastMessageTime(ChatMessageType type) {
        return chatMessages.stream()
                .filter(message -> message.getType() == type)
                .mapToLong(ChatMessage::getTime)
                .max()
                .orElse(0);
    }

    @Override
    public void clearChatMessages() {
        chatMessages.clear();
    }

    @Override
    public void removeLatestMessagesBatch() {
        List<ChatMessage> messages = chatMessages;
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).getType() == ChatMessageType.GOD) {
                chatMessages.subList(i, messages.size()).clear();
                break;
            }
        }
    }

    @Override
    public void addMessage(String message, String sender, ChatMessageType type) {
        long time = System.currentTimeMillis();

        ChatMessage chatMessage = new ChatMessage(message, sender, time, type);
        chatMessages.add(chatMessage);
        lastMessageTime = time;

        if (chatMessages.size() > 100) {
            chatMessages.remove(0);
        }
    }
}
