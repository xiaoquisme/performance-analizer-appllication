package com.example.performanceanalizerapplication.controllers;

import com.example.performanceanalizerapplication.client.WeComNotificationClient;
import com.example.performanceanalizerapplication.entity.JiraIssue;
import com.example.performanceanalizerapplication.service.JiraIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class JiraIssueController {
    private final JiraIssueService jiraIssueService;
    private final WeComNotificationClient weComNotificationClient;

    @PostMapping(":normal-check")
    public void getNotFillIssues(@RequestBody JiraIssueRequest request) {
        String sprintName = request.getSprintName();
        List<JiraIssue> jiraIssues = jiraIssueService.getNotFillTimeTrackingIssues(sprintName);
        weComNotificationClient.sendNotFillTimeTrackingNotification(jiraIssues, sprintName);

        List<JiraIssue> notFillEpicIssues = jiraIssueService.getNotFillEpicIssues(sprintName);
        weComNotificationClient.sendNotFillEpicNotification(notFillEpicIssues, sprintName);

//        // notfillfixVersionIssues
//        List<JiraIssue> notFillFixVersionIssues = jiraIssueService.getNotFillFixVersionIssues(sprintName);
//        weComNotificationClient.sendNotFillFixVersionNotification(notFillFixVersionIssues, sprintName);

        // backlogIssuesHadAssignee
        List<JiraIssue> backlogIssuesHadAssignee = jiraIssueService.getBacklogIssuesHadAssignee(sprintName);
        weComNotificationClient.sendBacklogIssuesHadAssigneeNotification(backlogIssuesHadAssignee, sprintName);
    }
}
