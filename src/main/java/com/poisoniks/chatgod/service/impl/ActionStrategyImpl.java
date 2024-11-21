package com.poisoniks.chatgod.service.impl;

import com.poisoniks.chatgod.ChatGod;
import com.poisoniks.chatgod.entity.ActionType;
import com.poisoniks.chatgod.service.ActionSelector;
import com.poisoniks.chatgod.service.ActionStrategy;
import com.poisoniks.chatgod.util.Factory;

public class ActionStrategyImpl implements ActionStrategy {
    private final ActionSelector actionSelector;

    public ActionStrategyImpl(ActionSelector actionSelector) {
        this.actionSelector = actionSelector;
    }

    @Override
    public void executeAction() {
        ActionType actionType = actionSelector.selectAction();
        switch (actionType) {
            case DIALOG:
                Factory.getDialogAction().execute();
                break;
            case QUIZ:
                Factory.getQuizAction().execute();
                break;
            case NONE:
                Factory.getNoneAction().execute();
                break;
            default:
                ChatGod.LOG.error("Unknown action type: " + actionType);
                break;
        }
    }
}
