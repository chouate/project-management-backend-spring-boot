package com.hps.taskservice.controllers;

import com.hps.taskservice.dtos.DailyChargeInfo;
import com.hps.taskservice.dtos.TaskUpdateDTO;
import com.hps.taskservice.entities.Task;
import com.hps.taskservice.services.interfaces.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/tasks/owner/{ownerId}/between")
    public ResponseEntity<Map<String, Object>> getOwnerAvailabilityBetweenDates(
            @PathVariable Long ownerId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        Map<String, Object> result = taskService.getOwnerAvailabilityBetweenDates(ownerId, startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tasks/owner/{ownerId}/details")
    public ResponseEntity<List<DailyChargeInfo>> getOwnerChargeDetails(
            @PathVariable Long ownerId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<DailyChargeInfo> details = taskService.getOwnerChargeDetails(ownerId, startDate, endDate);
        return ResponseEntity.ok(details);
    }

    @GetMapping("tasks/by-owner/{ownerId}")
    public ResponseEntity<List<Task>> getTasksByOwner(@PathVariable Long ownerId) {
        List<Task> tasks = taskService.getTasksByOwnerId(ownerId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{id}/updatedCompletionPercentage")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO dto) {
        Task updated = taskService.updateTaskMinimal(id, dto);
        return ResponseEntity.ok(updated);
    }

}
