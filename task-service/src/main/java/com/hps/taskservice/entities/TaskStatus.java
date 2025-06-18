package com.hps.taskservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TaskStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Task> listTask;

    public TaskStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
