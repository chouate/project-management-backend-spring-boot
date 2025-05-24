package com.hps.userservice.repositories;

import com.hps.userservice.entities.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {
}
