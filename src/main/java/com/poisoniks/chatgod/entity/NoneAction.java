package com.poisoniks.chatgod.entity;

import com.poisoniks.chatgod.service.AIConnector;
import com.poisoniks.chatgod.service.ChatManager;

public class NoneAction extends AbstractAction {
    public NoneAction(ChatManager chatManager, AIConnector aiConnector) {
        super(chatManager, aiConnector);
    }

    @Override
    public void execute() {
        // Do nothing
    }
}
