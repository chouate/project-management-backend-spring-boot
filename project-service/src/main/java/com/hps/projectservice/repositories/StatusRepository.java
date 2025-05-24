package com.hps.projectservice.repositories;

import com.hps.projectservice.entities.StatusPhase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StatusRepository extends JpaRepository<StatusPhase, Integer> {
    public Optional<StatusPhase> findStatusByName(String name);
}
