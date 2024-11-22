package com.poisoniks.chatgod.action;

import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.event.ChatGodMessageEvent;
import com.poisoniks.chatgod.exception.EmptyResponseException;
import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.service.impl.PromptGenerator;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class DialogAction extends AbstractAction {
    private static final String TASK_DIALOG = """
        You have the power to write messages in the chat in minecraft.
        "You can discuss something with players, react to events, joke, give advices, etc. Be creative.
        You can fulfill small favors for players, for example remind them to do something or to tell the information from another player when they were offline.
        Your response should be a json {"message":string}, where string is your response (only message without time stamps or any other elements).
        Try to give short responses. Responses should be 1-3 sentences. DO NOT USE EMOJI!
        You are allowed to give responses with {"message":"empty"}, if you don't have anything to say.
        It is not necessary to respond to every message or end the response with a question.
        Use all the context provided to make your messages contain more sense, not only respond to the last message.
        """;

    public DialogAction(ChatManager chatManager, AIConnector aiConnector) {
        super(chatManager, aiConnector);
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

        if (response.getMessage().equals("empty")) {
            MinecraftForge.EVENT_BUS.post(new ChatGodMessageEvent("*ChatGod had nothing to say*"));
            return;
        }

        chatManager.sendServerMessageFromGod(response.getMessage());
        MinecraftForge.EVENT_BUS.post(new ChatGodMessageEvent(response.getMessage()));
    }
}
