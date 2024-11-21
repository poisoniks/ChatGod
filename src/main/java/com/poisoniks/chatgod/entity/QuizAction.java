package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.util.ChatHelper;

public class QuizAction extends AbstractAction {
    public QuizAction(ChatManager chatManager, AIConnector aiConnector, ChatHelper chatHelper) {
        super(chatManager, aiConnector, chatHelper);
    }

    @Override
    public void execute() {

    }

}
