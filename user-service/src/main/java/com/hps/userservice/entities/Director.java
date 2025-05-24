package com.hps.userservice.entities;

import com.hps.userservice.models.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Director extends User{
    private String DirectorAttribut;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<Developer> listDevelopersAffected ;

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<ProjectManager> ListProjectManagersAffected ;

    @Transient
    private List<Client> listClientsCreated;

}
