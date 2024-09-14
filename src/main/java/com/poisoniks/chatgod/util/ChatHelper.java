package com.poisoniks.chatgod.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class ChatHelper {
    public void sendServerMessage(String message) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null) {
            server.getConfigurationManager().sendChatMsg(new ChatComponentText(message));
        }
    }
}
