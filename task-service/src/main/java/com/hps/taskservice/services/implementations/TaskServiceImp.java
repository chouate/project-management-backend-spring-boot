package com.hps.taskservice.services.implementations;

import com.hps.taskservice.dtos.DailyChargeInfo;
import com.hps.taskservice.dtos.TaskUpdateDTO;
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

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
    public Map<String, Object> getOwnerAvailabilityBetweenDates(Long ownerId, Date startDate, Date endDate, Long excludeTaskId) {
        List<Task> tasks = taskRepository.findByOwnerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                ownerId, endDate, startDate);

        if(excludeTaskId != null){
            tasks = tasks.stream()
                    .filter(t -> !t.getId().equals(excludeTaskId))
                    .collect(Collectors.toList());
        }

        int totalOverlapDays = 0;
        long durationInDays = 0;

        // Calculer les jours ouvrables (hors week-ends) dans la période demandée
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                durationInDays++;
            }
            calendar.add(Calendar.DATE, 1);
        }

        // Calculer la charge réelle sur les jours ouvrables
        for (Task task : tasks) {
            Date taskStart = task.getStartDate();
            Date taskEnd = task.getEndDate();

            Date effectiveStart = taskStart.after(startDate) ? taskStart : startDate;
            Date effectiveEnd = taskEnd.before(endDate) ? taskEnd : endDate;

//            long overlap = ChronoUnit.DAYS.between(effectiveStart.toInstant(), effectiveEnd.toInstant()) + 1;
//            totalOverlapDays += overlap;
            calendar.setTime(effectiveStart);

            while (!calendar.getTime().after(effectiveEnd)) {
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    totalOverlapDays++;
                }
                calendar.add(Calendar.DATE, 1);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("chargeDays", totalOverlapDays);
        result.put("maxDays", durationInDays);
        result.put("isAvailable", totalOverlapDays < durationInDays);
        result.put("comment", "Load: " + totalOverlapDays + " days out of " + durationInDays);

        return result;
    }

    @Override
    public List<DailyChargeInfo> getOwnerChargeDetails(Long ownerId, Date startDate, Date endDate, Long excludeTaskId) {
        List<Task> tasks = taskRepository.findByOwnerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                ownerId, endDate, startDate);

        if (excludeTaskId != null) {
            tasks = tasks.stream()
                    .filter(t -> !t.getId().equals(excludeTaskId))
                    .collect(Collectors.toList());
        }

        List<DailyChargeInfo> results = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                Date currentDay = calendar.getTime();
                boolean available = true;
                String taskName = null;
                String projectName = null;

                for (Task task : tasks) {
                    if (!task.getStartDate().after(currentDay) && !task.getEndDate().before(currentDay)) {
                        available = false;
                        taskName = task.getName();
                        try {
                            Project project = projectRestClient.getProjectById(task.getProjectId());
                            projectName = project.getName();
                        } catch (Exception e) {
                            projectName = "(Projet introuvable)";
                        }
                        break;
                    }
                }

                String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(currentDay);
                results.add(new DailyChargeInfo(formattedDate, available, taskName, projectName));
            }
            calendar.add(Calendar.DATE, 1);
        }

        return results;
    }


    @Override
    public void deleteTask(Long id) {
        Task taskExisting = getTaskById(id);
        this.taskRepository.delete(taskExisting);
    }

    @Override
    public List<Task> getTasksByOwnerId(Long ownerId) {
        List<Task> tasks = taskRepository.findByOwnerId(ownerId);

        for (Task task : tasks) {
            if (task.getProjectId() != null) {
                Project p = this.projectRestClient.getProjectById(task.getProjectId());
                task.setProjectName(p.getName());
            }
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
    public Task updateTaskMinimal(Long id, TaskUpdateDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (dto.getCompletionPercentage() != null) {
            task.setCompletionPercentage(dto.getCompletionPercentage());
        }

        // Mettre à jour le statut à partir du nom fourni dans le DTO
        if (dto.getStatus() != null) {
            TaskStatus status = taskStatusRepository.findByName(dto.getStatus())
                    .orElseThrow(() -> new ResourceNotFoundException("TaskStatus not found with name: " + dto.getStatus()));
            task.setStatus(status);
        }

        //task.setLastModified(new Date()); // facultatif
        return taskRepository.save(task);
    }


}
