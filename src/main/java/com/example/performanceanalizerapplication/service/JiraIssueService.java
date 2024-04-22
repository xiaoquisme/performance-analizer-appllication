package com.example.performanceanalizerapplication.service;

import com.example.performanceanalizerapplication.entity.FixVersion;
import com.example.performanceanalizerapplication.entity.JiraIssue;
import com.example.performanceanalizerapplication.entity.JiraWorklog;
import com.example.performanceanalizerapplication.entity.Label;
import com.example.performanceanalizerapplication.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@RequiredArgsConstructor
public class JiraIssueService {
    private final JiraIssueRepository issueRepository;
    private final JiraWorklogRepository worklogRepository;
    private final FixVersionRepository fixVersionRepo;
    private final LableRepository labelRepo;

    public List<JiraIssue> getNotFillTimeTrackingIssues(String sprintName) {
        Map<Long, JiraIssue> issues = issueRepository.findAllByCurrentSprintAndStatusIs(sprintName, "IN DEV").stream().collect(Collectors.toMap(JiraIssue::getId, Function.identity()));
        List<Long> notFillTimeTrackingIssueIds = issues.values().stream().filter(item -> isBlank(item.getTimetrackingSpent())).map(JiraIssue::getId).toList();
        List<Long> issueIds = issues.values().stream()
                .map(JiraIssue::getId)
                .filter(item -> !notFillTimeTrackingIssueIds.contains(item))
                .toList();
        List<JiraWorklog> jiraWorklogs = worklogRepository.findAllByIssueIdIn(issueIds);
        Map<Long, List<JiraWorklog>> issueWithWorklogsMap = jiraWorklogs.stream().collect(Collectors.groupingBy(JiraWorklog::getIssueId));
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");

        List<Long> jiraIssueIdsWithoutWorklog =
                issueWithWorklogsMap.keySet()
                        .stream()
                        .filter(issueId -> {
                            List<JiraWorklog> worklogs = issueWithWorklogsMap.get(issueId);
                            return worklogs.stream().allMatch(item -> {
                                try {
                                    Date date = dateformat.parse(item.getCreated());
                                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return localDate.isAfter(LocalDate.now().minusDays(1));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        })
                        .toList();
        return Stream.of(jiraIssueIdsWithoutWorklog, notFillTimeTrackingIssueIds)
                .flatMap(Collection::stream)
                .distinct()
                .map(issues::get)
                .toList();
    }

    // getNotFillEpicIssues
    public List<JiraIssue> getNotFillEpicIssues(String sprintName) {
        return issueRepository.findAllByCurrentSprint(sprintName).stream().filter(item -> isBlank(item.getEpicKey())).toList();
    }

    public List<JiraIssue> getNotFillFixVersionIssues(String sprintName) {
        List<JiraIssue> list = issueRepository.findAllByCurrentSprint(sprintName).stream().toList();
        List<FixVersion> fixVersions = fixVersionRepo.findAllByIssueKeyIn(list.stream().map(JiraIssue::getKey).toList());
        return list.stream().filter(item -> fixVersions.stream().noneMatch(fixVersion -> fixVersion.getIssueKey().equals(item.getKey()))).toList();

    }

    public List<JiraIssue> getBacklogIssuesHadAssignee(String sprintName) {
        return issueRepository.findAllByCurrentSprintAndStatusIsAndAssigneeIsNotNull(sprintName, "BACKLOG");
    }

    public List<JiraIssue> getNotFillLablesIssues(String sprintName) {
        List<JiraIssue> allByCurrentSprint = issueRepository.findAllByCurrentSprint(sprintName);
        List<Label> labels = labelRepo.findAllByIssueKeyIn(allByCurrentSprint.stream().map(JiraIssue::getKey).toList());
        return allByCurrentSprint.stream().filter(item -> labels.stream().noneMatch(fixVersion -> fixVersion.getIssueKey().equals(item.getKey()))).toList();
    }
}
