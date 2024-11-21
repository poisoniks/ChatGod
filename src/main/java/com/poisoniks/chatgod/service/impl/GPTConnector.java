package com.poisoniks.chatgod.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poisoniks.chatgod.Config;
import com.poisoniks.chatgod.exception.ApiKeyIsEmptyException;
import com.poisoniks.chatgod.exception.EmptyResponseException;
import com.poisoniks.chatgod.exception.FatalException;
import com.poisoniks.chatgod.exception.InvalidRequestBodyException;
import com.poisoniks.chatgod.entity.*;
import com.poisoniks.chatgod.service.AIConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GPTConnector implements AIConnector {
    @Override
    public <T extends AIResponse> T callForResponse(String prompt, Class<T> responseType) {
        return callForResponse(getConnectionParameters(prompt), responseType);
    }

    public <T extends AIResponse> T callForResponse(ConnectionParameters params, Class<T> responseType) {
        try {
            URL obj = new URL(params.getUrl());

            if (params.getApiKey().trim().isEmpty()) {
                throw new ApiKeyIsEmptyException("API key is not set. Please set the API key in the configuration file.");
            }

            BufferedReader br = getReader(params, createRequest(params), obj);
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString(), responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getReader(ConnectionParameters params, GPTRequestBody request, URL obj) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + params.getApiKey());
        connection.setRequestProperty("Content-Type", "application/json");

        String body = new Gson().toJson(request);

        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();

        int code = connection.getResponseCode();

        if (code == 400) {
            throw new InvalidRequestBodyException("Invalid request body");
        }

        if (code != 200) {
            throw new FatalException("Failed to connect to GPT API. Response code: " + code);
        }

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    public <T extends AIResponse> T extractMessageFromJSONResponse(String json, Class<T> type) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        GPTResponse response = gson.fromJson(json, GPTResponse.class);
        String message = response.getChoices().get(0).getMessage().getContent();

        return gson.fromJson(sanitizeMessage(message), type);
    }

    private GPTRequestBody createRequest(ConnectionParameters params) {
        GPTRequestBody request = new GPTRequestBody();
        request.setModel(params.getModel());
        request.setTemperature(0.7);
        GPTRequestBody.Message message = new GPTRequestBody.Message("user", params.getPrompt());
        request.setMessages(Collections.singletonList(message));
        return request;
    }

    private String sanitizeMessage(String message) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("‘", "'");
        replacements.put("’", "'");
        replacements.put("“", "\"");
        replacements.put("”", "\"");
        replacements.put("–", "-");
        replacements.put("—", "-");
        replacements.put("…", "...");
        replacements.put("•", "*");

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        return message.replaceAll("[^\\x20-\\x7E]", "");
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
