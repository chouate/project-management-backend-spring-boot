package com.hps.userservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Technology {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(unique = true, nullable = false)
    @NotBlank(message = "the name of thrchnologie is required!! (Not Blank).")
    private String name;

    private String description;

    @ManyToMany(mappedBy = "technologyList")
    @JsonIgnore
    //@JsonBackReference //child/inverse side
    private Set<User> userList = new HashSet<>();
}
