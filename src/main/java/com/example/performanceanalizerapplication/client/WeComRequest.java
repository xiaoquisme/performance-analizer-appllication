package com.example.performanceanalizerapplication.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

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
        private List<String> mentioned_mobile_list;
    }

    public static WeComRequest create(String message) {
        return new WeComRequest("markdown", new Markdown(message, Arrays.asList("@all", "17681803219")));
    }
}
