package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.ChatGod;
import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.ai.ApiKeyIsEmptyException;
import com.poisoniks.chatgod.ai.EmptyResponseException;
import com.poisoniks.chatgod.ai.FatalException;
import com.poisoniks.chatgod.ai.GPTConnector;
import com.poisoniks.chatgod.entity.ChatMessage;
import com.poisoniks.chatgod.entity.ChatMessageType;
import com.poisoniks.chatgod.entity.ConnectionParameters;
import com.poisoniks.chatgod.event.ChatGodMessageEvent;
import com.poisoniks.chatgod.service.AIService;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.util.ChatHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.time.Duration;
import java.util.List;

public class AIServiceImpl implements AIService {
    private static final long MINUTES_10 = Duration.ofMinutes(10).toMillis();
    private static final String PROMPT_BASE = """
            You are a chat God. You are on the minecraft server and have the power to write messages in the chat in minecraft.
            You are proud, but funny and sarcastic. If you don't see any messages to reply or do not want to reply, leave a 'null' message.
            Discuss something with players, joke, use context, be creative. Messages from 'ChatGod' are yours.
            Your response should be what people should see in chat. Like '<God> Hi'. DO NOT USE EMOJI!
            Your goal is to entertain the players on the server. Server specific context:
            %s
            Here is the chat history:
            """;
    private final ChatManager chatManager;
    private final GPTConnector gptConnector;
    private final ChatHelper chatHelper;

    public AIServiceImpl(ChatManager chatManager, GPTConnector gptConnector, ChatHelper chatHelper) {
        this.chatManager = chatManager;
        this.gptConnector = gptConnector;
        this.chatHelper = chatHelper;
    }

    @Override
    public void processChatHistory() {
        if (!shouldCallAi(chatManager)) {
            return;
        }

        List<ChatMessage> messages = chatManager.getChatMessages();

        String prompt = generatePrompt(messages);
        String response;
        try {
            response = gptConnector.callForResponse(getConnectionParameters(prompt));
        } catch (EmptyResponseException e) {
            return;
        } catch (ApiKeyIsEmptyException e) {
            ChatGod.LOG.error(e);
            throw new FatalException(e.getMessage());
        }
        chatHelper.sendServerMessage(response);

        MinecraftForge.EVENT_BUS.post(new ChatGodMessageEvent(response));
    }

    @Override
    public void removeLatestMessagesBatch() {
        chatManager.removeLatestMessagesBatch();
    }

    @Override
    public void clearChatHistory() {
        chatManager.clearChatMessages();
    }

    private boolean shouldCallAi(ChatManager chatManager) {
        List<ChatMessage> messages = chatManager.getChatMessages();

        if (MinecraftServer.getServer().getConfigurationManager().getCurrentPlayerCount() == 0) {
            return false;
        }

        if (messages.isEmpty()) {
            return false;
        }

        if (System.currentTimeMillis() - chatManager.getLastMessageTime() > MINUTES_10) {
            return false;
        }

        if (chatManager.getLatestMessage().getType() == ChatMessageType.GOD) {
            return false;
        }

        return true;
    }

    private String generatePrompt(List<ChatMessage> messages) {
        StringBuilder prompt = new StringBuilder();
        prompt.append(String.format(PROMPT_BASE, Config.context));
        for (ChatMessage message : messages) {
            prompt.append(message.getStringTime()).append("|").append(message.getSender()).append(": ").append(message.getMessage()).append("\\n");
        }
        return prompt.toString();
    }

    private ConnectionParameters getConnectionParameters(String prompt) {
        ConnectionParameters params = new ConnectionParameters();
        params.setUrl(Config.url);
        params.setApiKey(Config.apiKey);
        params.setModel(Config.model);
        params.setTemperature(Config.temperature);
        params.setPrompt(prompt);

        return params;
    }
}
