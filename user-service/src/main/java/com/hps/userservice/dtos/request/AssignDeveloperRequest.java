package com.hps.userservice.dtos.request;

import lombok.Data;

@Data
public class AssignDeveloperRequest {
    private Long developerId;
    private Long projectManagerId;
    private Long directorId;
}
