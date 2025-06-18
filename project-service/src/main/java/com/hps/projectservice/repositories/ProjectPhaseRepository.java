package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.ProjectPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase, Long> {
    List<ProjectPhase> findByProject_Id(Long projectId);
}