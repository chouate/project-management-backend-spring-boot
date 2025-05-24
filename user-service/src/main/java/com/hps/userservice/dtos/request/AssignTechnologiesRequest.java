package com.hps.userservice.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignTechnologiesRequest {
    private Long userId;
    private List<String> technologyNames;
}
