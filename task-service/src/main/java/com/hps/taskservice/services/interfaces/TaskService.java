package com.hps.taskservice.services.interfaces;

import com.hps.taskservice.entities.Task;

import java.util.List;

public interface TaskService {
    //List<Task> getAllTasks();
    List<Task> getTasksByProjectId(Long projectId);
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
