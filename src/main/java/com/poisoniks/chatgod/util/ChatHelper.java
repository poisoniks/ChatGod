package com.poisoniks.chatgod.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatHelper {
    public void sendServerMessage(String message) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server != null) {
            server.getConfigurationManager().sendChatMsg(new ChatComponentText(message));
        }
    }

    public void sendServerMessageFromGod(String message) {
        String formattedMessage = EnumChatFormatting.GOLD + "<ChatGod> " + EnumChatFormatting.RESET + message;
        sendServerMessage(formattedMessage);
    }
}
