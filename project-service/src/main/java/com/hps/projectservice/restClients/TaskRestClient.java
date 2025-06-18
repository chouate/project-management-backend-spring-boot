package com.hps.projectservice.restClients;

import com.hps.projectservice.models.Task;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "task-service")
public interface TaskRestClient {
    @CircuitBreaker(name = "taskService", fallbackMethod = "getDefaultTasksByProjectId")
    @GetMapping("/api/projects/{projectId}/tasks")
    public List<Task> getAllTasksByProjectId(@PathVariable Long projectId);



    default List<Task> getDefaultTasksByProjectId(Long projectId, Throwable throwable) {
        Task defaultTask = new Task();
        defaultTask.setId(-1L);
        defaultTask.setName("Aucune tâche disponible");
        defaultTask.setDescription("Erreur de communication avec le service des tâches.");
        defaultTask.setStatus(null);

        return List.of(defaultTask);
    }

    default Task getDefaultTaskById(Long id, Exception exception){
        Task task = new Task();

        task.setId(id);
        task.setName("Name Not Available");
        task.setDescription("Code Not Available");
        task.setStatus(null);
        return task;
    }
}
