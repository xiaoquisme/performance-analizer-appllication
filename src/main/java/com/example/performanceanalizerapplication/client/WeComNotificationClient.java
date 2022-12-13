package com.example.performanceanalizerapplication.client;

import com.example.performanceanalizerapplication.controllers.JiraIssueResponse;
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

    public void sendNotification(List<JiraIssueResponse> jiraIssueResponses) {
        if (CollectionUtils.isEmpty(jiraIssueResponses)) {
            return;
        }

        Table.Builder builder = new Table.Builder()
                .addRow("卡号", "邮箱");
        jiraIssueResponses.forEach(item -> builder.addRow(item.getCardNo(), item.getAssignee()));
        WeComRequest request = WeComRequest.create("IN DEV的这些卡，没有填 time tracking。 \n" + builder.build().toString());
        client.sendNotification(request);
    }

    @FeignClient(name = "WeComRobotClient", url = "${feign.wecom-robot}")
    interface WeComRobotClient {
        @PostMapping
        void sendNotification(WeComRequest request);
    }
}