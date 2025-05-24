package com.hps.projectservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private int estimateWorkDays;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date deliveryDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date creatAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastModified;
    @ManyToOne()
    private Phase phase;
    private String status;



}
