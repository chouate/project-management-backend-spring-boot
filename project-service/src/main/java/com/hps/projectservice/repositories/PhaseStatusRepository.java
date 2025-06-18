package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.StatusPhase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PhaseStatusRepository extends JpaRepository<StatusPhase, Long> {
    public Optional<StatusPhase> findStatusByName(String name);
}
