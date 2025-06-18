package com.hps.projectservice.services.implementations;

import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.repositories.PhaseStatusRepository;
import com.hps.projectservice.services.interfaces.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private final PhaseStatusRepository phaseStatusRepository;

    public StatusServiceImpl(PhaseStatusRepository phaseStatusRepository) {
        this.phaseStatusRepository = phaseStatusRepository;
    }

    @Override
    public List<StatusPhase> getAllStatuses() {
        return phaseStatusRepository.findAll();
    }

    @Override
    public StatusPhase getStatusById(Long id) {
        return phaseStatusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("StatusPhase not found with Id : "+id));
    }

    @Override
    public StatusPhase getStatusByName(String name) {
        return phaseStatusRepository.findStatusByName(name).orElseThrow(()-> new ResourceNotFoundException("StatusPhase not found whith name : "+name));
    }

    @Override
    public StatusPhase createNewStatus(StatusPhase status) {
        return phaseStatusRepository.save(status);
    }

    @Override
    public StatusPhase updateStatus(Long id, StatusPhase status) {
        StatusPhase existingStatus = phaseStatusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("StatusPhase, to update, not found with id : "+id));
        existingStatus.setName(status.getName());
        existingStatus.setDescription(status.getDescription());
        return phaseStatusRepository.save(existingStatus);
    }

    @Override
    public void deleteStatusById(Long id) {
        phaseStatusRepository.deleteById(id);
    }
}
