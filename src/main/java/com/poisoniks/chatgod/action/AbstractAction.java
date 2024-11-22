package com.poisoniks.chatgod.action;

import com.poisoniks.chatgod.entity.ChatMessage;
import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAction {
    protected final ChatManager chatManager;
    protected final AIConnector aiConnector;

    public AbstractAction(ChatManager chatManager, AIConnector aiConnector) {
        this.chatManager = chatManager;
        this.aiConnector = aiConnector;
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
