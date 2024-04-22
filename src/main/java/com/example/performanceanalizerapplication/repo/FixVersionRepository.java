package com.example.performanceanalizerapplication.repo;

import com.example.performanceanalizerapplication.entity.FixVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixVersionRepository extends JpaRepository<FixVersion, Long> {
    List<FixVersion> findAllByIssueKeyIn(List<String> issueKeys);
}
