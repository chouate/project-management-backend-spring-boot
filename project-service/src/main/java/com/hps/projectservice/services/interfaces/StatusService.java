package com.hps.projectservice.services.interfaces;

import com.hps.projectservice.entities.StatusPhase;

import java.util.List;

public interface StatusService {
    public List<StatusPhase> getAllStatuses();
    public StatusPhase getStatusById(Long id);
    public StatusPhase getStatusByName(String name);
    public StatusPhase createNewStatus(StatusPhase status);
    public StatusPhase updateStatus(Long id, StatusPhase status);
    public void deleteStatusById(Long id);
}
