package com.poisoniks.chatgod.ai;

import com.poisoniks.chatgod.entity.ConnectionParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GPTConnector {
    public String callForResponse(ConnectionParameters params) {
        try {
            URL obj = new URL(params.getUrl());

            if (params.getApiKey().trim().isEmpty()) {
                throw new ApiKeyIsEmptyException("API key is not set. Please set the API key in the configuration file.");
            }

            BufferedReader br = getReader(params, obj);
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getReader(ConnectionParameters params, URL obj) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + params.getApiKey());
        connection.setRequestProperty("Content-Type", "application/json");

        String body = "{\"model\": \"" + params.getModel() + "\", \"temperature\": " + params.getTemperature() + ", \"messages\": [{\"role\": \"user\", \"content\": \"" + params.getPrompt() + "\"}]}";
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }

    public String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        String result = response.substring(start, end);
        if (result.equals("null")) {
            throw new EmptyResponseException("Empty response from GPT");
        }

        return result;
    }
}
