package com.hps.userservice.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignToProjectManagerRequest {
    private Long projectManagerId;
    private List<Long> developerIds;
    private Long directorId;
}
