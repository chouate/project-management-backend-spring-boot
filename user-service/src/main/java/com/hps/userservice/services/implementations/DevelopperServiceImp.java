package com.hps.userservice.services.implementations;

import com.hps.userservice.entities.Developer;
import com.hps.userservice.repositories.DeveloperRepository;
import com.hps.userservice.services.interfaces.DevelopperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevelopperServiceImp implements DevelopperService {
    private DeveloperRepository developerRepository;

    public DevelopperServiceImp(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public List<Developer> getAllDeveloppers() {
        return developerRepository.findAll();
    }

    @Override
    public Developer getDevelopperById(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    @Override
    public Developer createDeveloper(Developer developper) {
        return developerRepository.save(developper);
    }

    @Override
    public Developer updateDevelpper(Long id, Developer developper) {
        developper.setId(id);
        return developerRepository.save(developper);
    }

    @Override
    public void deleteDevelopperById(Long id) {
        developerRepository.deleteById(id);
    }
}
