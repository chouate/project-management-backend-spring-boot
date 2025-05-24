package com.hps.userservice.dtos.lightDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DeveloperLightDTO   {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
