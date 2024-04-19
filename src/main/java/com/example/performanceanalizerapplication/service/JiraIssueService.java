package com.example.performanceanalizerapplication.service;

import com.example.performanceanalizerapplication.entity.JiraIssue;
import com.example.performanceanalizerapplication.entity.JiraWorklog;
import com.example.performanceanalizerapplication.repo.JiraEpicRepository;
import com.example.performanceanalizerapplication.repo.JiraIssueRepository;
import com.example.performanceanalizerapplication.repo.JiraWorklogRepository;
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
    private final JiraEpicRepository epicRepository;

    public List<JiraIssue> getNotFillTimeTrackingIssues(String sprintName) {
        Map<Long, JiraIssue> issues = issueRepository.findAllByCurrentSprintAndStatusIs(sprintName, "IN DEV").stream().collect(Collectors.toMap(JiraIssue::getId, Function.identity()));
        List<Long> notFillTimeTrackingIssueIds = issues.values().stream().filter(item -> isBlank(item.getTimetrackingSpent())).map(JiraIssue::getId).toList();
        List<Long> issueIds = issues.values().stream()
                .map(JiraIssue::getId)
                .filter(item -> !notFillTimeTrackingIssueIds.contains(item))
                .toList();
        List<JiraWorklog> jiraWorklogs = worklogRepository.findAllByIssueIdIn(issueIds);
        Map<Long, List<JiraWorklog>> issueWithWorklogsMap = jiraWorklogs.stream().collect(Collectors.groupingBy(JiraWorklog::getIssueId));
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS-0600");

        List<Long> jiraIssueIdsWithoutWorklog =
                issueWithWorklogsMap.keySet()
                        .stream()
                        .filter(issueId -> {
                            List<JiraWorklog> worklogs = issueWithWorklogsMap.get(issueId);
                            return worklogs.stream().allMatch(item -> {
                                try {
                                    Date date = dateformat.parse(item.getCreated());
                                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return localDate.isBefore(LocalDate.now());
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
        Map<Long, JiraIssue> issues = issueRepository.findAllByCurrentSprint(sprintName).stream().collect(Collectors.toMap(JiraIssue::getId, Function.identity()));
        return issues.values().stream().filter(item -> isBlank(item.getEpicKey())).toList();
    }

//    public List<JiraIssue> getNotFillFixVersionIssues(String sprintName) {
//        Map<Long, JiraIssue> issues = issueRepository.findAllByCurrentSprint(sprintName).stream().collect(Collectors.toMap(JiraIssue::getId, Function.identity()));
//        return issues.values().stream().filter(item -> CollectionUtils.isEmpty(item.getFixVersions())).toList();
//    }

    public List<JiraIssue> getBacklogIssuesHadAssignee(String sprintName) {
        return issueRepository.findAllByCurrentSprintAndStatusIsAndAssigneeIsNotNull(sprintName, "BACKLOG");
    }
}
