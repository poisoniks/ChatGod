package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.entity.ChatMessageType;
import com.poisoniks.chatgod.event.ChatGodMessageEvent;
import com.poisoniks.chatgod.service.ChatManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ChatActivityListener {
    private final ChatManager chatManager;

    public ChatActivityListener(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        chatManager.addMessage(event.message, event.player.getDisplayName(), ChatMessageType.PLAYER);
    }

    @SubscribeEvent
    public void onChatGodMessage(ChatGodMessageEvent event) {
        chatManager.addMessage(event.getMessage(), "ChatGod", ChatMessageType.GOD);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        chatManager.addMessage(event.player.getDisplayName() + " joined the game", "Server", ChatMessageType.JOIN);
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        chatManager.addMessage(event.player.getDisplayName() + " left the game", "Server", ChatMessageType.LEAVE);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer) {
            chatManager.addMessage(((EntityPlayer) event.entity).getDisplayName() + " died", "Server", ChatMessageType.DEATH);
        }
    }
}
