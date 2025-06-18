package com.hps.projectservice.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class Task {

    private Long id;
    private String name;
    private String description;

    private int progress;
    private int completionPercentage;
    private int actualWorkDays;
    private int assignmentRate; // 100% or 50%
    private int estimatedWorkDays;

    private Date startDate;
    private Date endDate;

    private TaskStatus status;

    private Long projectId;


    private Long ownerId;
    private User owner;

}
