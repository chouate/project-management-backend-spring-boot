package com.hps.userservice.services.interfaces;

import com.hps.userservice.entities.ProjectManager;

import java.util.List;

public interface ProjectManagerService {
    List<ProjectManager> getAllProjectManagers();
    ProjectManager getProjetcManagerById(Long id);
    ProjectManager createProjetcManager(ProjectManager projectManager);
    ProjectManager updateProjetcManager(Long id, ProjectManager projectManager);
    void deleteProjetcManagerById(Long id);

}
