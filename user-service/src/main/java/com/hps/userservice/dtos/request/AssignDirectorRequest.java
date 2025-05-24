package com.hps.userservice.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignDirectorRequest {
    private Long directorId;
    private List<Long> projectManagerIds;
    private List<Long> developerIds;
}
