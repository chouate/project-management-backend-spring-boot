package com.hps.taskservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Project {
    private Long id;
    private String name;
    private String description;
    private String status;
}
