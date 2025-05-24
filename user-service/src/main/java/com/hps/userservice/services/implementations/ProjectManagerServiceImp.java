package com.hps.userservice.services.implementations;

import com.hps.userservice.entities.ProjectManager;
import com.hps.userservice.repositories.ProjectManagerRepository;
import com.hps.userservice.services.interfaces.ProjectManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagerServiceImp implements ProjectManagerService {
    private final ProjectManagerRepository projectManagerRepository;

    public ProjectManagerServiceImp(ProjectManagerRepository projectManagerRepository) {
        this.projectManagerRepository = projectManagerRepository;
    }

    @Override
    public List<ProjectManager> getAllProjectManagers() {
        return projectManagerRepository.findAll();
    }

    @Override
    public ProjectManager getProjetcManagerById(Long id) {
        return projectManagerRepository.findById(id).orElse(null);
    }

    @Override
    public ProjectManager createProjetcManager(ProjectManager projectManager) {
        return projectManagerRepository.save(projectManager);
    }

    @Override
    public ProjectManager updateProjetcManager(Long id, ProjectManager projectManager) {
        return projectManagerRepository.save(projectManager);
    }

    @Override
    public void deleteProjetcManagerById(Long id) {
        projectManagerRepository.deleteById(id);
    }
}
