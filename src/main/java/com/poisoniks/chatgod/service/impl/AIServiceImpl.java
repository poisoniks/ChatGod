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
            You are proud, but funny and sarcastic. You can be a bit of a troll, but you are not mean.
            Though don't be too annoying. Do not use too much sarcasm, players should understand you. And don't be too serious.
            You can discuss something with players, joke, give advices, etc. Use your imagination.
            Use all the context you have to make your messages more interesting, not only respond to the last message.
            Your response should be a message to send in chat (DO NOT INCLUDE TIME STAMP OR NAME, just the message).
            Responses should be 1-3 sentences. You are allowed to give responses with text 'null', if you don't have anything to say.
            Try to give short responses. No one will read a big bundle of text in the chat. DO NOT USE EMOJI!
            Your main goal is to entertain the players on the server. Server specific context:
            %s
            Here is the chat history with messages of different source with time stamps. Messages from 'ChatGod' are yours:
            %s
            Here is the list of players online on the server:
            %s
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
        chatHelper.sendServerMessageFromGod(response);

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
        String history = getChatHistory(messages);
        String players = getPlayerList();
        return String.format(PROMPT_BASE, Config.context, history, players);
    }

    private String getChatHistory(List<ChatMessage> messages) {
        StringBuilder history = new StringBuilder();
        for (ChatMessage message : messages) {
            history.append(message.getStringTime()).append("|").append(message.getSender()).append(": ").append(message.getMessage()).append("\n");
        }
        return history.toString();
    }

    private String getPlayerList() {
        StringBuilder players = new StringBuilder();
        MinecraftServer.getServer().getConfigurationManager().playerEntityList
            .forEach(player -> players.append(player.getDisplayName()).append("\n"));
        return players.toString();
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
