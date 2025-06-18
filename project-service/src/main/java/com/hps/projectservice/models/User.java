package com.hps.projectservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String phoneNumber;
    private String role;
}
