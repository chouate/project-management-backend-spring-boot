package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
