package com.example.performanceanalizerapplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "jira_issue_label")
public class Label {
    @Id
    private Long id;
    private String name;
    private String issueKey;
}
