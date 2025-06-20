package com.hps.projectservice.services.implementations;

import com.hps.projectservice.dtos.ProjectWithTasksDTO;
import com.hps.projectservice.entities.Phase;
import com.hps.projectservice.entities.Project;
import com.hps.projectservice.entities.ProjectPhase;
import com.hps.projectservice.entities.StatusPhase;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.models.Client;
import com.hps.projectservice.models.Task;
import com.hps.projectservice.models.User;
import com.hps.projectservice.repositories.PhaseRepository;
import com.hps.projectservice.repositories.ProjectPhaseRepository;
import com.hps.projectservice.repositories.ProjectRepository;
import com.hps.projectservice.repositories.PhaseStatusRepository;
import com.hps.projectservice.restClients.ClientRestClient;
import com.hps.projectservice.restClients.TaskRestClient;
import com.hps.projectservice.restClients.UserRestClient;
import com.hps.projectservice.services.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final PhaseRepository phaseRepository;
    private final PhaseStatusRepository phaseStatusRepository;
    private final UserRestClient userRestClient;
    private final ClientRestClient clientRestClient;
    private final TaskRestClient taskRestClient;
    private final ProjectPhaseRepository projectPhaseRepository;


    public ProjectServiceImpl(ProjectRepository projectRepository, PhaseRepository phaseRepository, PhaseStatusRepository phaseStatusRepository, UserRestClient userRestClient, ClientRestClient clientRestClient, TaskRestClient taskRestClient, ProjectPhaseRepository projectPhaseRepository) {
        this.projectRepository = projectRepository;
        this.phaseRepository = phaseRepository;
        this.phaseStatusRepository = phaseStatusRepository;
        this.userRestClient = userRestClient;
        this.clientRestClient = clientRestClient;
        this.taskRestClient = taskRestClient;
        this.projectPhaseRepository = projectPhaseRepository;
    }
    @Override
    public List<Project> getAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public ProjectWithTasksDTO getProjectWithTasks(Long projectId) {
        Project project = getProjectById(projectId); // utilise déjà les enrichissements (phase, PM, client)

        List<Task> tasks;
        try {
            tasks = taskRestClient.getAllTasksByProjectId(projectId);
        } catch (Exception e) {
            tasks = List.of(); // ou log si tu veux
        }

        return new ProjectWithTasksDTO(project, tasks);
    }


    @Override
    public List<Project> getAllProjectsByPMId(Long pmId) {

        List<Project> projects = this.projectRepository.findByProjectManagerId(pmId);

        for (Project project : projects) {
//            // Phase + Status
//            if (project.getPhase() != null && project.getPhase().getId() != null) {
//                Phase phase = phaseRepository.findById(project.getPhase().getId()).orElse(null);
//                if (phase != null && phase.getStatus() != null && phase.getStatus().getId() != null) {
//                    StatusPhase status = phaseStatusRepository.findById(phase.getStatus().getId()).orElse(null);
//                    phase.setStatus(status);
//                }
//                project.setPhase(phase);
//            }

            // Phase
            if (project.getPhase() != null && project.getPhase().getId() != null) {
                Phase phase = phaseRepository.findById(project.getPhase().getId()).orElse(null);
                project.setPhase(phase);
            }
            // Phase
            if (project.getStatusPhase() != null && project.getStatusPhase().getId() != null) {
                StatusPhase statusPhase = phaseStatusRepository.findById(project.getStatusPhase().getId()).orElse(null);
                project.setStatusPhase(statusPhase);
            }

            List<ProjectPhase> phases = projectPhaseRepository.findByProject_Id(project.getId());
            project.setProjectPhases(phases);

            // Project Manager
            if (project.getProjectManagerId() != null) {
                User pm = userRestClient.getPMById(project.getProjectManagerId());
                project.setProjectManager(pm);
            }

            // Client
            if (project.getClientId() != null) {
                Client client = clientRestClient.getClientById(project.getClientId());
                project.setClient(client);
            }

            // 🆕 Tasks
            try {
                List<Task> tasks = taskRestClient.getAllTasksByProjectId(project.getId());
                project.setTasks(tasks);
            } catch (Exception e) {
                project.setTasks(List.of());
            }
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long id) {
        Project project = this.projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The project not found with Id: " + id));

//        // Phase + Status
//        if (project.getPhase() != null && project.getPhase().getId() != null) {
//            Phase phase = phaseRepository.findById(project.getPhase().getId()).orElse(null);
//            if (phase != null && phase.getStatus() != null && phase.getStatus().getId() != null) {
//                StatusPhase status = phaseStatusRepository.findById(phase.getStatus().getId()).orElse(null);
//                phase.setStatus(status);
//            }
//            project.setPhase(phase);
//        }

        // Phase
        if (project.getPhase() != null && project.getPhase().getId() != null) {
            Phase phase = phaseRepository.findById(project.getPhase().getId()).orElse(null);
            project.setPhase(phase);
        }
        // Status Phase
        if (project.getStatusPhase() != null && project.getStatusPhase().getId() != null) {
            StatusPhase statusPhase = phaseStatusRepository.findById(project.getStatusPhase().getId()).orElse(null);
            project.setStatusPhase(statusPhase);
        }

        List<ProjectPhase> phases = projectPhaseRepository.findByProject_Id(project.getId());
        project.setProjectPhases(phases);

        // Project Manager
        if (project.getProjectManagerId() != null) {
            User pm = userRestClient.getPMById(project.getProjectManagerId());
            project.setProjectManager(pm);
        }

        // Client
        if (project.getClientId() != null) {
            Client client = clientRestClient.getClientById(project.getClientId());
            project.setClient(client);
        }

        // 🆕 Tasks
        try {
            List<Task> tasks = taskRestClient.getAllTasksByProjectId(project.getId());
            project.setTasks(tasks);
        } catch (Exception e) {
            project.setTasks(List.of()); // ou null si tu préfères
        }

        return project;
    }

    @Override
    public Project createProject(Project projectInput) {

//        // Manage the phase (if presente)
//        if (projectInput.getPhase() != null && projectInput.getPhase().getId() != null) {
//            Phase phase = phaseRepository.findById(projectInput.getPhase().getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Phase not found with id: " + projectInput.getPhase().getId()));
//
//            // Verify if the phase status is provided
//            if (projectInput.getPhase().getStatus() != null && projectInput.getPhase().getStatus().getId() != null) {
//                StatusPhase status = phaseStatusRepository.findById(projectInput.getPhase().getStatus().getId())
//                        .orElseThrow(() -> new ResourceNotFoundException("StatusPhase not found with id: " + projectInput.getPhase().getStatus().getId()));
//
//                // Affected the status to the phase
//                phase.setStatus(status);
//            }
//
//            // Affected completed phase to project
//            projectInput.setPhase(phase);
//        }

        // Manage the phase (if presente)
        if (projectInput.getPhase() != null && projectInput.getPhase().getId() != null) {
            Phase phase = phaseRepository.findById(projectInput.getPhase().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Phase not found with id: " + projectInput.getPhase().getId()));
            // Affected completed phase to project
            projectInput.setPhase(phase);
        }
        // Manage the statusPhase (if presente)
        if (projectInput.getStatusPhase() != null && projectInput.getStatusPhase().getId() != null) {
            StatusPhase statusPhase = phaseStatusRepository.findById(projectInput.getStatusPhase().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("StatusPhase not found with id: " + projectInput.getStatusPhase().getId()));
            // Affected completed phase to project
            projectInput.setStatusPhase(statusPhase);
        }

        if (projectInput.getProjectManagerId() != null) {
            User pm = validateAndGetProjectManager(projectInput.getProjectManagerId());
            projectInput.setProjectManager(pm); // ne sera pas persisté, juste visible dans la réponse
        }

        if(projectInput.getClientId() != null){
            Client c = validateAndGetClient(projectInput.getClientId());
            projectInput.setClient(c);
        }
        // Save the project
        return projectRepository.save(projectInput);
    }



    @Override
    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject  = this.getProjectById(id);

        existingProject .setName(updatedProject.getName());
        existingProject .setDescription(updatedProject.getDescription());
        existingProject .setStatus(updatedProject.getStatus());
        existingProject .setProgress(updatedProject.getProgress());
        existingProject .setActualWorkDays(updatedProject.getActualWorkDays());
        existingProject .setEstimatedWorkDays(updatedProject.getEstimatedWorkDays());
        existingProject .setDuration(updatedProject.getDuration());
        existingProject .setStartDate(updatedProject.getStartDate());
        existingProject .setEndDate(updatedProject.getEndDate());
        existingProject .setDeliveryDate(updatedProject.getDeliveryDate());
        existingProject .setCreatAt(updatedProject.getCreatAt());
        existingProject .setLastModified(updatedProject.getLastModified());
        existingProject .setProjectManagerId(updatedProject.getProjectManagerId());
        existingProject .setClientId(updatedProject.getClientId());

//        // Phase
//        if (updatedProject.getPhase() != null && updatedProject.getPhase().getId() != null) {
//            Phase phase = phaseRepository.findById(updatedProject.getPhase().getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Phase not found with id: " + updatedProject.getPhase().getId()));
//
//            // Phase Status
//            if (updatedProject.getPhase().getStatus() != null && updatedProject.getPhase().getStatus().getId() != null) {
//                StatusPhase status = phaseStatusRepository.findById(updatedProject.getPhase().getStatus().getId())
//                        .orElseThrow(() -> new ResourceNotFoundException("StatusPhase not found with id: " + updatedProject.getPhase().getStatus().getId()));
//
//                phase.setStatus(status);
//            }
//
//            existingProject.setPhase(phase);
//        }

        // Phase
        if (updatedProject.getPhase() != null && updatedProject.getPhase().getId() != null) {
            Phase phase = phaseRepository.findById(updatedProject.getPhase().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Phase not found with id: " + updatedProject.getPhase().getId()));

            existingProject.setPhase(phase);
        }

        // Status Phase
        if (updatedProject.getStatusPhase() != null && updatedProject.getStatusPhase().getId() != null) {
            StatusPhase statusPhase = phaseStatusRepository.findById(updatedProject.getStatusPhase().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("StatusPhase not found with id: " + updatedProject.getStatusPhase().getId()));

            existingProject.setStatusPhase(statusPhase);
        }

        if (updatedProject.getProjectManagerId() != null) {
            User pm = validateAndGetProjectManager(updatedProject.getProjectManagerId());
            updatedProject.setProjectManager(pm); // ne sera pas persisté, juste visible dans la réponse
            existingProject.setProjectManager(updatedProject.getProjectManager());
        }

        if(updatedProject.getClientId() != null){
            Client c = validateAndGetClient(updatedProject.getClientId());
            existingProject.setClient(c);
        }

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long id) {
        Project existing = getProjectById(id);
        projectRepository.delete(existing);
    }

    @Override
    public void recalculateActualWorkDays(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id : "+projectId));

        // Appel vers task-service pour récupérer toutes les tâches du projet
        List<Task> tasks = taskRestClient.getAllTasksByProjectId(project.getId());
        //List<TaskDTO> tasks = taskClient.getTasksByProjectId(projectId);

        int totalActualWorkDays = 0;
        for (Task task : tasks) {
            totalActualWorkDays += task.getActualWorkDays();
        }

        // Calcul et mise à jour du progrès du projet (peut dépasser 100 %)
        if (project.getEstimatedWorkDays() > 0) {
            int progress = Math.round((totalActualWorkDays * 100.0f) / project.getEstimatedWorkDays());
            project.setProgress(progress);
        } else {
            project.setProgress(0); // ou null selon la logique métier
        }

        project.setActualWorkDays(totalActualWorkDays);
        projectRepository.save(project);
    }


    //*******************************************************
    private User validateAndGetProjectManager(Long projectManagerId) {
        try {
            return userRestClient.getPMById(projectManagerId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Project Manager not found with id: " + projectManagerId);
        }
    }

    private Client validateAndGetClient(Long clientId) {
        try {
            return clientRestClient.getClientById(clientId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Client not found with id: " + clientId);
        }
    }

}
