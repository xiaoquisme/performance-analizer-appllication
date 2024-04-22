package com.example.performanceanalizerapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "jira_issue_fix_version")
public class FixVersion {
    @Id
    private Long id;
    private String name;
    private String issueKey;
}
