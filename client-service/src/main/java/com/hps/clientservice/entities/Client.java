package com.hps.clientservice.entities;

import com.hps.clientservice.models.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "the name is required!! (Not Blank).")
    private String name;
    @Column(unique = true, nullable = true)
    private String code;
    private String activityDomain;

    private String contactName;
    @Email(message = "Please provide a valid email address")
    private String contactEmail;
    @Pattern(regexp = "0[0-9]{9}$", message = "Contact phone number require 10 numbers 0*********")
    private String contactPhoneNumber;
    private Date startDate;
    private Date endDateEstimate;

    private Long projectManagerId; // 🔗 lien vers l'utilisateur (chef de projet)
    private Long directorId;  // 🔗 lien vers l'utilisateur (directeur)
    @Transient
    private User projectManager;
    @Transient
    private User director;

}
