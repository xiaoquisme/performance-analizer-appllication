package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LableRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByIssueKeyIn(List<String> issueKeys);
}
