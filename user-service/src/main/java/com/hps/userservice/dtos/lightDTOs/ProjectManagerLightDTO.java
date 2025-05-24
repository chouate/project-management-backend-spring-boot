package com.hps.userservice.dtos.lightDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ProjectManagerLightDTO  {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public ProjectManagerLightDTO(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
