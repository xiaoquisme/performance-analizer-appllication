package com.example.performanceanalizerapplication.controllers;

import com.example.performanceanalizerapplication.client.WeComNotificationClient;
import com.example.performanceanalizerapplication.service.JiraIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JiraIssueController {
    private final JiraIssueService jiraIssueService;
    private final WeComNotificationClient weComNotificationClient;

    @PostMapping("/issues:not-fill-time-tracking")
    public List<JiraIssueResponse> getNotFillIssues(@RequestParam(name = "sprintName") String sprintName) {
        List<JiraIssueResponse> jiraIssueResponses = jiraIssueService.getNotFillTimeTrackingIssues(sprintName).stream()
                .map(item -> JiraIssueResponse.builder()
                        .assignee(item.getAssignee())
                        .cardNo(item.getKey())
                        .build()
                )
                .toList();
        weComNotificationClient.sendNotification(jiraIssueResponses);
        return jiraIssueResponses;
    }
}
