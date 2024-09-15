package com.poisoniks.chatgod.service;

import com.poisoniks.chatgod.entity.ChatMessage;
import com.poisoniks.chatgod.entity.ChatMessageType;

import java.util.List;

public interface ChatManager {
    List<ChatMessage> getChatMessages();
    List<ChatMessage> getChatMessages(ChatMessageType... types);
    ChatMessage getLatestMessage();
    ChatMessage getLatestMessage(ChatMessageType type);
    long getLastMessageTime();
    long getLastMessageTime(ChatMessageType type);
    void clearChatMessages();
    void removeLatestMessagesBatch();
}
