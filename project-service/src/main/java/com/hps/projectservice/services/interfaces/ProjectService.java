package com.hps.projectservice.services.interfaces;

import com.hps.projectservice.dtos.ProjectWithTasksDTO;
import com.hps.projectservice.entities.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    List<Project> getAllProjectsByPMId(Long pmId);
    Project getProjectById(Long id);
    ProjectWithTasksDTO getProjectWithTasks(Long projectId);
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long id);
}
