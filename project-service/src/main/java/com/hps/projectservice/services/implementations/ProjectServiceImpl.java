package com.hps.projectservice.services.implementations;

import com.hps.projectservice.entities.Project;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.repositories.ProjectRepository;
import com.hps.projectservice.services.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The project not found with Id: "+id));
    }

    @Override
    public Project createProject(Project project) {
        return this.projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        Project existing = this.getProjectById(id);
        existing.setName(project.getName());
        existing.setDescription(project.getDescription());
        existing.setProgress(project.getProgress());
        existing.setActualWorkDays(project.getActualWorkDays());
        existing.setEstimateWorkDays(project.getEstimateWorkDays());
        existing.setStartDate(project.getStartDate());
        existing.setEndDate(project.getEndDate());
        existing.setDeliveryDate(project.getDeliveryDate());
        existing.setCreatAt(project.getCreatAt());
        existing.setLastModified(project.getLastModified());
        existing.setPhase(project.getPhase());
        existing.setStatus(project.getStatus());
        return projectRepository.save(existing);
    }

    @Override
    public void deleteProject(Long id) {
        Project existing = getProjectById(id);
        projectRepository.delete(existing);
    }
}
