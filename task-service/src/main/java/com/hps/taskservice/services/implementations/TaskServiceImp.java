package com.hps.taskservice.services.implementations;

import com.hps.taskservice.entities.Task;
import com.hps.taskservice.entities.TaskStatus;
import com.hps.taskservice.exceptions.ResourceNotFoundException;
import com.hps.taskservice.models.Project;
import com.hps.taskservice.models.Technology;
import com.hps.taskservice.models.User;
import com.hps.taskservice.repositories.TaskRepository;
import com.hps.taskservice.repositories.TaskStatusRepository;
import com.hps.taskservice.restClients.ProjectRestClient;
import com.hps.taskservice.restClients.UserRestClient;
import com.hps.taskservice.services.interfaces.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final ProjectRestClient projectRestClient;
    private final UserRestClient userRestClient;

    public TaskServiceImp(TaskRepository taskRepository, TaskStatusRepository taskStatusRepository, ProjectRestClient projectRestClient, UserRestClient userRestClient) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.projectRestClient = projectRestClient;
        this.userRestClient = userRestClient;
    }

    @Override
    public List<Task> getTasksByProjectId(Long projectId) {
        List<Task> tasks = this.taskRepository.findByProjectId(projectId);

        for (Task task : tasks) {
//            if (task.getProjectId() != null) {
//                Project p = this.projectRestClient.getProjectById(task.getProjectId());
//                task.setProject(p);
//            }
            if (task.getOwnerId() != null) {
                User owner = this.userRestClient.getOwnerById(task.getOwnerId());
                task.setOwner(owner);
            }
            if (task.getTechnologyId() != null) {
                Technology tech = userRestClient.getTechnologyById(task.getTechnologyId());
                task.setTechnology(tech);
            }

        }

        return tasks;
    }

    @Override
    public Task getTaskById(Long id) {
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

//        if (task.getProjectId() != null) {
//            Project p = this.projectRestClient.getProjectById(task.getProjectId());
//            task.setProject(p);
//        }

        if (task.getOwnerId() != null) {
            User owner = this.userRestClient.getOwnerById(task.getOwnerId());
            task.setOwner(owner);
        }

        if (task.getTechnologyId() != null) {
            Technology tech = userRestClient.getTechnologyById(task.getTechnologyId());
            task.setTechnology(tech);
        }

        return task;
    }

    @Override
    public Task createTask(Task task) {
        //Manage the taskStatus if present
        if (task.getStatus() != null && task.getStatus().getId() != null) {
            TaskStatus status = this.taskStatusRepository.findById(task.getStatus().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found with id: "+task.getStatus().getId()));
            task.setStatus(status);
        }

        if(task.getOwnerId() != null){
            User owner = this.userRestClient.getOwnerById(task.getOwnerId());
            task.setOwner(owner);
        }
        if (task.getTechnologyId() != null) {
            Technology tech = userRestClient.getTechnologyById(task.getTechnologyId());
            task.setTechnology(tech);
        }

//        //Save the task
//        return this.taskRepository.save(task);
        // Sauvegarder la tâche
        Task savedTask = this.taskRepository.save(task);

        // ⚠️ Recalculer seulement si actualWorkDays > 0
        if (savedTask.getActualWorkDays() != 0 && savedTask.getActualWorkDays() > 0) {
            projectRestClient.recalculateActualWorkDays(savedTask.getProjectId());
        }

        return savedTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task taskExisting = getTaskById(id);

        taskExisting.setName(updatedTask.getName());
        taskExisting.setDescription(updatedTask.getDescription());
        taskExisting.setProgress(updatedTask.getProgress());
        taskExisting.setCompletionPercentage(updatedTask.getCompletionPercentage());
        taskExisting.setActualWorkDays(updatedTask.getActualWorkDays());
        taskExisting.setAssignmentRate(updatedTask.getAssignmentRate());
        taskExisting.setEstimatedWorkDays(updatedTask.getEstimatedWorkDays());
        taskExisting.setDuration(updatedTask.getDuration());
        taskExisting.setStartDate(updatedTask.getStartDate());
        taskExisting.setEndDate(updatedTask.getEndDate());
        // Task Status
        if(updatedTask.getStatus() != null && updatedTask.getStatus().getId() != null){
            TaskStatus status = this.taskStatusRepository.findById(updatedTask.getStatus().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found with id: "+updatedTask.getStatus().getId()));
            taskExisting.setStatus(status);
        }

        taskExisting.setProjectId(updatedTask.getProjectId());
//        if(updatedTask.getProjectId() != null){
//            Project p = this.projectRestClient.getProjectById(updatedTask.getProjectId());
//            taskExisting.setProject(p);
//        }

        taskExisting.setOwnerId(updatedTask.getOwnerId());
        if(updatedTask.getOwnerId() != null){
            User owner = this.userRestClient.getOwnerById(updatedTask.getOwnerId());
            taskExisting.setOwner(owner);
        }

        taskExisting.setTechnologyId(updatedTask.getTechnologyId());
        if(updatedTask.getTechnologyId() != null){
            Technology tech = userRestClient.getTechnologyById(updatedTask.getTechnologyId());
            taskExisting.setTechnology(tech);
        }

        Task savedTask = this.taskRepository.save(taskExisting);

        // Appel vers project-service pour recalculer le champ actualWorkDays
        projectRestClient.recalculateActualWorkDays(savedTask.getProjectId());

        return savedTask;
    }

    @Override
    public void deleteTask(Long id) {
        Task taskExisting = getTaskById(id);
        this.taskRepository.delete(taskExisting);
    }
}
