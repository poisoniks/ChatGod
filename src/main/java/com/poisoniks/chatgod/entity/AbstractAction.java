package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAction {
    protected final ChatManager chatManager;
    protected final AIConnector aiConnector;
    protected final ChatHelper chatHelper;

    public AbstractAction(ChatManager chatManager, AIConnector aiConnector, ChatHelper chatHelper) {
        this.chatManager = chatManager;
        this.aiConnector = aiConnector;
        this.chatHelper = chatHelper;
    }

    public abstract void execute();

    protected String getChatHistory() {
        StringBuilder history = new StringBuilder();
        List<ChatMessage> chatMessages = chatManager.getChatMessages();
        for (ChatMessage message : chatMessages) {
            history.append(message.getStringTime()).append("|").append(message.getSender()).append(": ").append(message.getMessage()).append("\n");
        }
        return history.toString();
    }

    protected List<String> getPlayerList() {
        return MinecraftServer.getServer().getConfigurationManager().playerEntityList
            .stream()
            .map(EntityPlayer::getDisplayName)
            .collect(Collectors.toList());
    }
}
