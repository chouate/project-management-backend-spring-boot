package com.hps.projectservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TaskStatus {
    private int id;
    private String name;
    private String description;
}
