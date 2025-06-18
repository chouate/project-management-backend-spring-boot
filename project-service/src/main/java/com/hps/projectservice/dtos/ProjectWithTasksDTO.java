package com.hps.projectservice.dtos;

import com.hps.projectservice.entities.Project;
import com.hps.projectservice.models.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectWithTasksDTO {
    private Project project;
    private List<Task> tasks;
}
