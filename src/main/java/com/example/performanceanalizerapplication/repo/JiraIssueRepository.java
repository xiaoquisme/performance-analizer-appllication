package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.JiraIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JiraIssueRepository extends JpaRepository<JiraIssue, Long> {
    List<JiraIssue> findAllByCurrentSprint(String currentSprint);

    List<JiraIssue> findAllByCurrentSprintAndStatusIs(String sprintName, String status);

    List<JiraIssue> findAllByCurrentSprintAndStatusIsAndAssigneeIsNotNull(String sprintName, String status);

}