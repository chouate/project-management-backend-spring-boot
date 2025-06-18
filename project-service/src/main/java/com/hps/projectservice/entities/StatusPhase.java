package com.hps.projectservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class StatusPhase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true)
    private String name;
    private String description;
//    @OneToMany(mappedBy = "status")
//    @JsonIgnore
//    private List<Phase> listPhase;

    public StatusPhase(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
