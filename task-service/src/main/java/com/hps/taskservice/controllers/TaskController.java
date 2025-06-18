package com.hps.taskservice.controllers;

import com.hps.taskservice.entities.Task;
import com.hps.taskservice.services.interfaces.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getAllTaskByProjectId(@PathVariable Long projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteProject(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
