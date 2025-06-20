package com.hps.taskservice.restClients;


import com.hps.taskservice.models.Project;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "project-service")
public interface ProjectRestClient {
    @CircuitBreaker(name = "projectService", fallbackMethod = "getDefaultProjectById")
    @GetMapping("/api/projects/{id}")
    public Project getProjectById(@PathVariable Long id);

    default Project getDefaultProjectById(Long id, Exception exception){
        Project project = new Project();

        project.setId(id);
        project.setName("Name Not Available");
        project.setDescription("Description Not Available");
        project.setStatus("Status Domain Not Available");
        return project;
    }

    @PutMapping("/api/projects/{id}/recalculate-actual-workdays")
    void recalculateActualWorkDays(@PathVariable("id") Long projectId);


}
