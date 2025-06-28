package com.hps.taskservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TaskUpdateDTO {
    private Integer completionPercentage;
    private String status;
}
