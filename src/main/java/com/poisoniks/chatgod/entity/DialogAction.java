package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.event.ChatGodMessageEvent;
import com.poisoniks.chatgod.exception.EmptyResponseException;
import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.service.impl.PromptGenerator;
import com.poisoniks.chatgod.util.ChatHelper;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class DialogAction extends AbstractAction {
    private static final String TASK_DIALOG = """
        You have the power to write messages in the chat in minecraft.
        "You can discuss something with players, joke, give advices, etc. Be creative.
        Use all the context you have to make your messages more interesting, not only respond to the last message.
        Your response should be a json {"message":string}, where string is your response (only message without time stamps or any other elements).
        Responses should be 1-3 sentences. You are allowed to give responses with text 'null', if you don't have anything to say.
        Try to give short responses. No one will read a big bundle of text in the chat. DO NOT USE EMOJI!
        """;

    public DialogAction(ChatManager chatManager, AIConnector aiConnector, ChatHelper chatHelper) {
        super(chatManager, aiConnector, chatHelper);
    }

    @Override
    public void execute() {
        String history = getChatHistory();
        List<String> players = getPlayerList();
        String prompt = PromptGenerator.prompt()
            .withServerSpecificContext(Config.context)
            .withListOfPlayersOnline(players)
            .withChatHistory(history)
            .withTask(TASK_DIALOG)
            .build();

        DialogResponse response;
        try {
            response = aiConnector.callForResponse(prompt, DialogResponse.class);
        } catch (EmptyResponseException e) {
            return;
        }

        chatHelper.sendServerMessageFromGod(response.getMessage());
        MinecraftForge.EVENT_BUS.post(new ChatGodMessageEvent(response.getMessage()));
    }
}
