package com.poisoniks.chatgod.service;

import com.poisoniks.chatgod.entity.AIResponse;

public interface AIConnector {
    <T extends AIResponse> T callForResponse(String prompt, Class<T> responseType);
}
