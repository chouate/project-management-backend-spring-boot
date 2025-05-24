package com.hps.clientservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClientDTO {
    private String name;
    private String code;
    private String activityDomain;
    private String contactName;
    private String contactEmail;
    private String contactPhoneNumber;
    private Date startDate;
    private Date endDateEstimate;
    private Long projectManagerId;
    private Long directorId;
}
