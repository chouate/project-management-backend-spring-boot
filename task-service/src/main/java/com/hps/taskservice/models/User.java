package com.hps.taskservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String role;
}
