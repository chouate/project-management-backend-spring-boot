package com.hps.taskservice.services.interfaces;

import com.hps.taskservice.dtos.DailyChargeInfo;
import com.hps.taskservice.dtos.TaskUpdateDTO;
import com.hps.taskservice.entities.Task;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskService {
    //List<Task> getAllTasks();
    List<Task> getTasksByProjectId(Long projectId);
    Task getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    Map<String, Object> getOwnerAvailabilityBetweenDates(Long ownerId, Date startDate, Date endDate, Long excludeTaskId);
    List<DailyChargeInfo> getOwnerChargeDetails(Long ownerId, Date startDate, Date endDate, Long excludeTaskId);
    List<Task> getTasksByOwnerId(Long ownerId);
    Task updateTaskMinimal(Long id, TaskUpdateDTO dto);
}
