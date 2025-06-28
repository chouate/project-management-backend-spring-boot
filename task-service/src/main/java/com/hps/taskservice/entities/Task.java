package com.hps.taskservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hps.taskservice.models.Project;
import com.hps.taskservice.models.Technology;
import com.hps.taskservice.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The name of task is required.")
    private String name;
    private String description;

    private int progress;
    private int completionPercentage;
    private int actualWorkDays;
    private int assignmentRate; // 100% or 50%
    private int estimatedWorkDays; // jrs/homme
    private int duration; // jrs

    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creatAt;
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastModified;

    @ManyToOne
    private TaskStatus status;

    private Long projectId;
//    @Transient
//    private Project project;

    private String projectName;

    private Long ownerId;
    @Transient
    private User owner;

    private Long technologyId; // ID de la technologie (FK)
    @Transient
    private Technology technology;

}
