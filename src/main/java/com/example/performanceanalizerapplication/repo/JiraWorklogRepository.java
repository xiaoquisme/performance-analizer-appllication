package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.JiraWorklog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JiraWorklogRepository extends JpaRepository<JiraWorklog, Long> {
    List<JiraWorklog> findAllByIssueIdIn(List<Long> issueIds);
}