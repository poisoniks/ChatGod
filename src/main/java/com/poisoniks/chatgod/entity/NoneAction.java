package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;
import com.poisoniks.chatgod.util.ChatHelper;

public class NoneAction extends AbstractAction {
    public NoneAction(ChatManager chatManager, AIConnector aiConnector, ChatHelper chatHelper) {
        super(chatManager, aiConnector, chatHelper);
    }

    @Override
    public void execute() {
        // Do nothing
    }
}
