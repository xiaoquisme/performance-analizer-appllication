package com.example.performanceanalizerapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jira_sprint")
@Getter
@Setter
public class JiraSprint {
    @Id
    private Long id;
    private Long boardId;
    private String state;
    private String name;
    private String goal;
}
