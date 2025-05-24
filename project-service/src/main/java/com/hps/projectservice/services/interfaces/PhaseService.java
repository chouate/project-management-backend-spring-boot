package com.hps.projectservice.services.interfaces;

import com.hps.projectservice.entities.Phase;

import java.util.List;

public interface PhaseService {
    public List<Phase> getAllPhases();
    public Phase getPhaseById(Integer id);
    public Phase getPhaseByName(String name);
    public Phase createNewPhase(Phase phase);
    public Phase updatePhase(Integer id, Phase phase);
    public void deletePhaseById(Integer id);
}
