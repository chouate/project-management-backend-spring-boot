package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    public Phase findPhaseByName(String name);
}
