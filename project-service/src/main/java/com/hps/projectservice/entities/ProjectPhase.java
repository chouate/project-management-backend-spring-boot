package com.hps.projectservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPhase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Phase phase;

    @ManyToOne
    private StatusPhase statusPhase;

    private Date assignedAt;
    private Date completedAt;
}
