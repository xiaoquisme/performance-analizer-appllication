package com.example.performanceanalizerapplication.client;

import com.example.performanceanalizerapplication.entity.JiraIssue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.table.Table;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeComNotificationClient {
    private final WeComRobotClient client;

    public void sendNotFillTimeTrackingNotification(List<JiraIssue> jiraIssueResponses, String sprintName) {
        if (CollectionUtils.isEmpty(jiraIssueResponses)) {
            return;
        }

        Table.Builder builder = new Table.Builder()
                .addRow("卡号", "邮箱");
        jiraIssueResponses.forEach(item -> builder.addRow(item.getKey(), item.getAssignee()));
        WeComRequest request = WeComRequest.create(String.format("在sprint %s IN DEV的这些卡，没有填 time tracking。 \n", sprintName) + builder.build().toString());
        client.sendNotification(request);
    }

    public void sendNotFillEpicNotification(List<JiraIssue> notFillEpicIssues, String sprintName) {
        if (CollectionUtils.isEmpty(notFillEpicIssues)) {
            return;
        }

        Table.Builder builder = new Table.Builder()
                .addRow("卡号", "邮箱");
        notFillEpicIssues.forEach(item -> builder.addRow(item.getKey(), item.getReporter()));
        WeComRequest request = WeComRequest.create(String.format("在sprint %s 的这些卡，没有填 epic。 \n", sprintName) + builder.build().toString());
        client.sendNotification(request);
    }

    public void sendNotFillFixVersionNotification(List<JiraIssue> notFillFixVersionIssues, String sprintName) {
        if (CollectionUtils.isEmpty(notFillFixVersionIssues)) {
            return;
        }

        Table.Builder builder = new Table.Builder()
                .addRow("卡号", "邮箱");
        notFillFixVersionIssues.forEach(item -> builder.addRow(item.getKey(), item.getReporter()));
        WeComRequest request = WeComRequest.create(String.format("在sprint %s 的这些卡，没有填 fix version。 \n", sprintName) + builder.build().toString());
        client.sendNotification(request);
    }

    public void sendBacklogIssuesHadAssigneeNotification(List<JiraIssue> backlogIssuesHadAssignee, String sprintName) {
        if (CollectionUtils.isEmpty(backlogIssuesHadAssignee)) {
            return;
        }

        Table.Builder builder = new Table.Builder()
                .addRow("卡号", "邮箱");
        backlogIssuesHadAssignee.forEach(item -> builder.addRow(item.getKey(), item.getAssignee()));
        WeComRequest request = WeComRequest.create(String.format("在sprint %s  backlog 的这些卡，已经有DEV开发了。 \n", sprintName) + builder.build().toString());
        client.sendNotification(request);
    }

    @FeignClient(name = "WeComRobotClient", url = "${feign.wecom-robot}")
    interface WeComRobotClient {
        @PostMapping
        void sendNotification(WeComRequest request);
    }
}