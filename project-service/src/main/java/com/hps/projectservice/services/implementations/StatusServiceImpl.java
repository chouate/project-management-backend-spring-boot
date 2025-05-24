package com.hps.projectservice.services.implementations;

import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.repositories.StatusRepository;
import com.hps.projectservice.services.interfaces.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<StatusPhase> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public StatusPhase getStatusById(Integer id) {
        return statusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("StatusPhase not found with Id : "+id));
    }

    @Override
    public StatusPhase getStatusByName(String name) {
        return statusRepository.findStatusByName(name).orElseThrow(()-> new ResourceNotFoundException("StatusPhase not found whith name : "+name));
    }

    @Override
    public StatusPhase createNewStatus(StatusPhase status) {
        return statusRepository.save(status);
    }

    @Override
    public StatusPhase updateStatus(Integer id, StatusPhase status) {
        StatusPhase existingStatus = statusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("StatusPhase, to update, not found with id : "+id));
        existingStatus.setName(status.getName());
        existingStatus.setDescription(status.getDescription());
        return statusRepository.save(existingStatus);
    }

    @Override
    public void deleteStatusById(Integer id) {
        statusRepository.deleteById(id);
    }
}
