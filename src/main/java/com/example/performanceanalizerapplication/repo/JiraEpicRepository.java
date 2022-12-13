package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.JiraEpic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JiraEpicRepository extends JpaRepository<JiraEpic, Long> {
}