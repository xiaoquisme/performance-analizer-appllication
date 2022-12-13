package com.example.performanceanalizerapplication.controllers;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JiraIssueResponse {
    private String cardNo;
    private String assignee;
}
