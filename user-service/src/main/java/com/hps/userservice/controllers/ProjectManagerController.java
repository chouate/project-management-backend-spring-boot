package com.hps.userservice.controllers;

import com.hps.userservice.entities.ProjectManager;
import com.hps.userservice.services.interfaces.ProjectManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@RequestMapping("/projectManagers")
public class ProjectManagerController {
    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    @GetMapping
    public List<ProjectManager> getAllProjectManagers(){
        return projectManagerService.getAllProjectManagers();
    }

    @GetMapping("/{id}")
    public ProjectManager getProjectManagerById(@PathVariable Long id){
        return projectManagerService.getProjetcManagerById(id);
    }

    @PostMapping()
    public ProjectManager createProjectManager(@RequestBody ProjectManager projectManager){
        return projectManagerService.createProjetcManager(projectManager);
    }


    @DeleteMapping("/{id}")
    public void deleteProjectManagerById(@PathVariable Long id){
        projectManagerService.deleteProjetcManagerById(id);
    }

    @PutMapping("/{id}")
    public ProjectManager updateProjectManager(@PathVariable Long id, @RequestBody ProjectManager projectManager){
        return projectManagerService.updateProjetcManager(id, projectManager);
    }

}
