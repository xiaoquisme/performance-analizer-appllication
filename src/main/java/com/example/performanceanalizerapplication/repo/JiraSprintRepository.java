package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.JiraSprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JiraSprintRepository extends JpaRepository<JiraSprint, Long> {
    JiraSprint findByName(String name);
}