package com.poisoniks.chatgod.entity;

import java.util.List;

public class GPTResponse {
    String id;
    String object;
    long created;
    String model;
    List<Choice> choices;
    Usage usage;
    String system_fingerprint;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getSystem_fingerprint() {
        return system_fingerprint;
    }

    public void setSystem_fingerprint(String system_fingerprint) {
        this.system_fingerprint = system_fingerprint;
    }

    public static class Choice {
        int index;
        Message message;
        String logprobs;
        String finish_reason;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public String getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(String logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
    }

    public static class Usage {
        int prompt_tokens;
        int completion_tokens;
        int total_tokens;
        CompletionTokensDetails completion_tokens_details;

        public int getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public int getCompletion_tokens() {
            return completion_tokens;
        }

        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public int getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }

        public CompletionTokensDetails getCompletion_tokens_details() {
            return completion_tokens_details;
        }

        public void setCompletion_tokens_details(CompletionTokensDetails completion_tokens_details) {
            this.completion_tokens_details = completion_tokens_details;
        }
    }

    public static class CompletionTokensDetails {
        int reasoning_tokens;

        public int getReasoning_tokens() {
            return reasoning_tokens;
        }

        public void setReasoning_tokens(int reasoning_tokens) {
            this.reasoning_tokens = reasoning_tokens;
        }
    }

    public static class Message {
        String role;
        String content;
        String refusal;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRefusal() {
            return refusal;
        }

        public void setRefusal(String refusal) {
            this.refusal = refusal;
        }
    }
}
