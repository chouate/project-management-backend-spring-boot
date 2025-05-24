package com.hps.userservice.dtos;

import com.hps.userservice.entities.Technology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long id;
    private String keycloakId;
    private String email;
    private String firstName;
    private String lastName;
    private String role; // "Director", "ProjectManager","Deveoper", "ADMIN"
   //private Set<String> technologies = new HashSet<>();
    private List<Technology> technologies;

}
