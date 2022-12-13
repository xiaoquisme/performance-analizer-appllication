package com.example.performanceanalizerapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jira_epic")
@Getter
@Setter
public class JiraEpic {
    @Id
    private Long id;
    private String issueId;
    private String updaterName;
    private String updateAuthor;
    private String created;
    private String timeSpent;
}
