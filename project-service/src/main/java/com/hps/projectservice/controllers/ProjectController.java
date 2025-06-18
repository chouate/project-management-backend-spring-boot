package com.hps.projectservice.controllers;

import com.hps.projectservice.dtos.ProjectWithTasksDTO;
import com.hps.projectservice.entities.Project;
import com.hps.projectservice.services.interfaces.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projectManagers/{pmId}/projects")
    public List<Project> getAllProjectsByPMId(@PathVariable Long pmId) {
        return projectService.getAllProjectsByPMId(pmId);
    }

    @GetMapping("/with-tasks/{id}")
    public ResponseEntity<ProjectWithTasksDTO> getProjectWithTasks(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectWithTasks(id));
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/projects/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
