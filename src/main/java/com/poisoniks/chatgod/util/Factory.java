package com.poisoniks.chatgod.util;

import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.entity.DialogAction;
import com.poisoniks.chatgod.entity.NoneAction;
import com.poisoniks.chatgod.entity.QuizAction;
import com.poisoniks.chatgod.service.*;
import com.poisoniks.chatgod.service.impl.*;
import com.poisoniks.chatgod.command.ChatGodControlCommand;

public class Factory {
    private static final AIService aiService;
    private static final ChatManager chatManager;
    private static final AIConnector aiConnector;
    private static final ChatGodThreadController chatGodThreadController;
    private static final ChatGodControlCommand chatGodControlCommand;
    private static final ChatActivityListener chatActivityListener;
    private static final ActionSelector actionSelector;
    private static final ActionStrategy actionStrategy;
    private static final DialogAction dialogAction;
    private static final QuizAction quizAction;
    private static final NoneAction noneAction;

    static {
        aiConnector = new GPTConnector();
        chatManager = new ChatManagerImpl();
        chatActivityListener = new ChatActivityListener(chatManager);
        actionSelector = new ActionSelectorImpl(chatManager);
        actionStrategy = new ActionStrategyImpl(actionSelector);
        dialogAction = new DialogAction(chatManager, aiConnector);
        quizAction = new QuizAction(chatManager, aiConnector);
        noneAction = new NoneAction(chatManager, aiConnector);
        aiService = new AIServiceImpl(chatManager, actionStrategy);
        chatGodThreadController = new ChatGodThreadController(Config.rate, aiService);
        chatGodControlCommand = new ChatGodControlCommand(chatGodThreadController);
    }

    public static AIService getAiService() {
        return aiService;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static AIConnector getAiConnector() {
        return aiConnector;
    }

    public static ChatGodThreadController getChatGodThreadController() {
        return chatGodThreadController;
    }

    public static ChatGodControlCommand getChatGodControlCommand() {
        return chatGodControlCommand;
    }
    public static ChatActivityListener getChatActivityListener() {
        return chatActivityListener;
    }
    public static ActionSelector getActionSelector() {
        return actionSelector;
    }
    public static ActionStrategy getActionStrategy() {
        return actionStrategy;
    }
    public static DialogAction getDialogAction() {
        return dialogAction;
    }
    public static QuizAction getQuizAction() {
        return quizAction;
    }
    public static NoneAction getNoneAction() {
        return noneAction;
    }
}
