package com.hps.userservice.dtos.request;

import lombok.Data;

@Data
public class AssignPMRequest {
    private Long projectManagerId;
    private Long directorId;
}
