package com.poisoniks.chatgod;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class Config {
    private static final String DEFAULT_CONTEXT = "Nothing specific";
    private static final String DEFAULT_API_KEY = "";
    private static final String DEFAULT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String DEFAULT_MODEL = "gpt-4o";
    private static final double DEFAULT_TEMPERATURE = 0.6;
    private static final int DEFAULT_RATE = 240000;
    private static final boolean DEFAULT_ENABLE_ON_SERVER_START = false;
    public static String context;
    public static String apiKey;
    public static String url;
    public static String model;
    public static double temperature;
    public static long rate;
    public static boolean enableOnServerStart;

    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            config.load();
            context = config.get("api", "context", DEFAULT_CONTEXT, "Server specific context for gpt model used in prompts").getString();
            apiKey = config.get("api", "apiKey", DEFAULT_API_KEY, "Api key to access gpt").getString();
            url = config.get("api", "url", DEFAULT_URL, "Url to access gpt").getString();
            model = config.get("api", "model", DEFAULT_MODEL, "Model to use for generating responses").getString();
            temperature = config.get("api", "temperature", DEFAULT_TEMPERATURE, "Temperature to use for gpt").getDouble();
            rate = config.get("api", "rate", DEFAULT_RATE, "Rate at which to send messages to gpt in milliseconds").getInt();
            enableOnServerStart = config.get("api", "enableOnServerStart", DEFAULT_ENABLE_ON_SERVER_START, "Enable ChatGod on server start").getBoolean();
        } catch (Exception e) {
            ChatGod.LOG.error("Failed to load configuration file!", e);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
        validateConfig();
    }

    private static void validateConfig() {
        if (context == null || context.isEmpty()) {
            throw new IllegalArgumentException("Context cannot be empty");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Api key cannot be empty");
        }
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Url cannot be empty");
        }
        if (model == null || model.isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (temperature < 0 || temperature > 1) {
            throw new IllegalArgumentException("Temperature must be between 0 and 1");
        }
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than 0");
        }
    }
}
