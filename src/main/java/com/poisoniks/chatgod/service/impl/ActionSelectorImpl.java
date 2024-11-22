package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.action.ActionType;
import com.poisoniks.chatgod.entity.ChatMessage;
import com.poisoniks.chatgod.entity.ChatMessageType;
import com.poisoniks.chatgod.service.ActionSelector;
import com.poisoniks.chatgod.service.ChatManager;
import net.minecraft.server.MinecraftServer;

import java.time.Duration;
import java.util.List;

public class ActionSelectorImpl implements ActionSelector {
    private static final long MINUTES_10 = Duration.ofMinutes(10).toMillis();
    private final ChatManager chatManager;

    public ActionSelectorImpl(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @Override
    public ActionType selectAction() {
        List<ChatMessage> messages = chatManager.getChatMessages();

        if (MinecraftServer.getServer().getConfigurationManager().getCurrentPlayerCount() == 0) {
            return ActionType.NONE;
        }

        if (messages.isEmpty()) {
            return ActionType.NONE;
        }

        if (chatManager.getLatestMessage().getType() == ChatMessageType.GOD) {
            return ActionType.NONE;
        }

        if (System.currentTimeMillis() - chatManager.getLastMessageTime() > MINUTES_10) {
            return ActionType.NONE;
        }

        return ActionType.DIALOG;
    }

}
