package com.hps.projectservice.services.interfaces;

import com.hps.projectservice.entities.Phase;

import java.util.List;

public interface PhaseService {
    public List<Phase> getAllPhases();
    public Phase getPhaseById(Long id);
    public Phase getPhaseByName(String name);
    public Phase createNewPhase(Phase phase);
    public Phase updatePhase(Long id, Phase phase);
    public void deletePhaseById(Long id);
}
