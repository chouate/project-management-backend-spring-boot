package com.hps.projectservice.services.implementations;

import com.hps.projectservice.entities.Phase;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.repositories.PhaseRepository;
import com.hps.projectservice.services.interfaces.PhaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseServiceImpl implements PhaseService {
    private final PhaseRepository phaseRepository;

    public PhaseServiceImpl(PhaseRepository phaseRepository) {
        this.phaseRepository = phaseRepository;
    }

    @Override
    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    @Override
    public Phase getPhaseById(Integer id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phase not found with id: " + id));
    }

    @Override
    public Phase getPhaseByName(String name) {
        return phaseRepository.findPhaseByName(name);
    }

    @Override
    public Phase createNewPhase(Phase phase) {
        return phaseRepository.save(phase);
    }

    @Override
    public Phase updatePhase(Integer id, Phase updatedPhase) {
        Phase existing = getPhaseById(id);
        existing.setName(updatedPhase.getName());
        existing.setDescription(updatedPhase.getDescription());
        existing.setStatus(updatedPhase.getStatus());
        return phaseRepository.save(existing);
    }

    @Override
    public void deletePhaseById(Integer id) {
        phaseRepository.deleteById(id);
    }

}
