package com.hps.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public abstract class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keycloakId; // Corresponds to "sub" in JWT Keycloak
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String image;

//    @Email @Column(nullable = false, unique = true)
//    private String email;
//    @NotBlank(message = "The pasword is required.") @Column(nullable = false)
//    private String password;
//    // Spring sevurity roles
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<UserRole> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    //@JsonManagedReference //parent/owner side
    private List<Technology> technologyList = new ArrayList<>();

}
