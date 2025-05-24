package com.hps.userservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class ProjectManager extends User{
    private String ProjectManagerAttribut;

    @ManyToOne()
    private Director director;

    @OneToMany(mappedBy = "projectManager")
    private List<Developer> listDeveloper ;


}
