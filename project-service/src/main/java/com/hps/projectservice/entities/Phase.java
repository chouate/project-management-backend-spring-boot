package com.hps.projectservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Phase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true)
    private String name;
    private String description;
//    @ManyToOne
//    private StatusPhase status;

//    @JsonIgnore
//    @OneToMany(mappedBy = "phase")
//    private List<Project> listProjects;

    public Phase(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
