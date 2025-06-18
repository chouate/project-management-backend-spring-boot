package com.hps.projectservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hps.projectservice.models.Client;
import com.hps.projectservice.models.Task;
import com.hps.projectservice.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The name of project is required.")
    private String name;
    private String description;

    private int progress;
    private int actualWorkDays;
    private int estimatedWorkDays;

    private Date startDate;
    private Date endDate;
    private Date deliveryDate;
    private Date creatAt;
    private Date lastModified;
    @ManyToOne()
    private Phase phase;
    @ManyToOne()
    private StatusPhase statusPhase;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPhase> projectPhases;

    private String status;

    private Long projectManagerId;
    @Transient
    private User projectManager;

    private Long clientId;
    @Transient
    private Client client;

    @Transient
    private List<Task> tasks;



}
