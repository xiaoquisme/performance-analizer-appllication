package com.example.performanceanalizerapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jira_issue")
@Getter
@Setter
public class JiraIssue {
    @Id
    private Long id;
    private Long epicId;
    private String epicKey;
    private String key;
    private String issueType;
    private String title;
    private String discription;
    private String timetrackingSpent;
    private String storyPoint;
    private String currentSprint;
    private String status;
    private String reporter;
    private String assignee;
}
