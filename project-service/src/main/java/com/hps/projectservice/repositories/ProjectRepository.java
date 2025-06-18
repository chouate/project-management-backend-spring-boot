package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectManagerId(Long id);
}
