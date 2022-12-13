package com.example.performanceanalizerapplication.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeComRequest {
    private String msgtype;
    private Markdown markdown;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Markdown {
        private String content;
    }

    public static WeComRequest create(String message) {
        return new WeComRequest("markdown", new Markdown(message));
    }
}
